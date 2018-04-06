/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.core;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2018 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.openbase.jps.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @param <V>
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractJavaProperty<V> implements Comparable<AbstractJavaProperty> { // <Value,ActionClass>

    private static final String NOT_IDENTIFIERED = "";
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final String[] propertyIdentifiers;
    protected final String[] argumentIdentifiers;
    protected final List<String> arguments;
    private final TreeMap<ValueType, Exception> errorReportMap;
    protected String identifier;
    private V value;
    private V parsedValue;
    private V applicationDefaultValue;
    private ValueType valueType, defaultValueType;
    private boolean parsed;
    public AbstractJavaProperty(String[] propertyIdentifier) {
        this.propertyIdentifiers = propertyIdentifier;
        this.argumentIdentifiers = generateArgumentIdentifiers();
        this.arguments = new ArrayList<>(argumentIdentifiers.length);
        this.identifier = NOT_IDENTIFIERED;
        this.applicationDefaultValue = null;
        this.defaultValueType = ValueType.PropertyDefault;
        this.valueType = ValueType.PropertyDefault;
        this.errorReportMap = new TreeMap<>(Comparator.comparingInt(Enum::ordinal));

        // preload default value.
        try {
            setValue(getPropertyDefaultValue(), ValueType.PropertyDefault);
        } catch (Exception ex) {
            addErrorReport(ex, ValueType.PropertyDefault);
        }
        this.reset();
    }

    protected boolean match(String commandIdentifier) {
        for (String command : propertyIdentifiers) {
            if (command.equals(commandIdentifier)) {
                identifier = commandIdentifier;
                return true;
            }
        }
        return false;
    }

    protected boolean neetToBeParsed() {
        return isIdentifiered() && !isParsed();
    }

    protected final void reset() {
        arguments.clear();
        parsed = false;
    }

    protected void addArgument(String arg) {
        arguments.add(arg);
    }

    protected void parseArguments() throws JPParsingException {
        try {
            valueType = ValueType.CommandLine;
            parsedValue = parse(Collections.unmodifiableList(arguments));
        } catch (Exception ex) {
            logger.error("Could not parse argument[" + identifier + "]!");
            logger.info("The valid syntax would be: " + getSyntax() + "\n");
            throw new JPParsingException("Could not parse argument[" + identifier + "]!", ex);
        }
        parsed = true;
    }

    public V getValue() {
        return value;
    }

    protected void setValue(final V value, final ValueType valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    public void update(final V value) {
        this.value = value;
        this.valueType = ValueType.Runtime;
    }

    protected void updateValue() throws JPServiceException {
        V newValue;
        switch (valueType) {
            case PropertyDefault:
                newValue = getPropertyDefaultValue();
                break;
            case ApplicationDefault:
                newValue = applicationDefaultValue;
                break;
            case CommandLine:
                newValue = parsedValue;
                break;
            case Runtime:
                return;
            default:
                throw new AssertionError(valueType + " is an unknown state!");
        }
        setValue(newValue, valueType);
    }

    protected void overwriteDefaultValue(V defaultValue) {
        this.applicationDefaultValue = defaultValue;
        this.defaultValueType = ValueType.ApplicationDefault;
        if (valueType.equals(ValueType.PropertyDefault)) {
            setValue(applicationDefaultValue, ValueType.ApplicationDefault);
        }
    }

    @Override
    public String toString() {
        String display = getClass().getSimpleName() + "[Identifier: " + identifier + " ";
        for (String arg : arguments) {
            display = display + "|" + arg;
        }
        display += "]";
        return display;
    }

    protected boolean isIdentifiered() {
        return !identifier.isEmpty();
    }

    public V getDefaultValue() throws JPNotAvailableException {
        switch (defaultValueType) {
            case ApplicationDefault:
                return applicationDefaultValue;
            default:
                return getPropertyDefaultValue();
        }
    }

    protected abstract String[] generateArgumentIdentifiers();

    protected String[] getPropertyIdentifiers() {
        return propertyIdentifiers;
    }

    protected String[] getArgumentIdentifiers() {
        return argumentIdentifiers;
    }

    public String getDefaultExample() {
        try {
            return propertyIdentifiers[0] + " " + getDefaultValue();
        } catch (JPNotAvailableException ex) {
            return propertyIdentifiers[0];
        }
    }

    public String getSyntax() {
        String syntax = "";
        for (int i = 0; i < propertyIdentifiers.length; i++) {
            syntax += propertyIdentifiers[i];
            for (String argumentIdentifier : argumentIdentifiers) {
                syntax += " '" + argumentIdentifier + "'";
            }
            if (i < propertyIdentifiers.length - 1) {
                syntax += " | ";
            }
        }
        return syntax;
    }

    public boolean isParsed() {
        return parsed;
    }

    @Override
    public int compareTo(AbstractJavaProperty o) {
        return propertyIdentifiers[0].compareTo(o.propertyIdentifiers[0]);
    }

    protected String getOneArgumentResult() throws JPBadArgumentException {
        checkArgumentCount(1);
        return arguments.get(0);
    }

    public ValueType getValueType() {
        return valueType;
    }

    protected void checkArgumentCount(int size) throws JPBadArgumentException {
        checkArgumentCount(size, size);
    }

    protected void checkArgumentCountMin(int min) throws JPBadArgumentException {
        if (arguments.size() < min) {
            throw new JPBadArgumentException("Missing property arguments!");
        }
    }

    protected void checkArgumentCountMax(int max) throws JPBadArgumentException {
        if (arguments.size() > max) {
            throw new JPBadArgumentException("To many property arguments!");
        }
    }

    protected void checkArgumentCount(int min, int max) throws JPBadArgumentException {
        int size = arguments.size();
        if (size < min) {
            throw new JPBadArgumentException("Missing property arguments!");
        } else if (size > max) {
            throw new JPBadArgumentException("To many property arguments!");
        }
    }

    protected void addErrorReport(final Exception exception, final ValueType valueType) {
        errorReportMap.put(valueType, exception);
    }

    protected Exception getErrorReport() {
        return errorReportMap.lastEntry().getValue();
    }

    protected Map<ValueType, Exception> getErrorReportMap() {
        return errorReportMap;
    }

    /**
     * Can be overwritten for value validation. Method is called after parsing.
     *
     * @throws JPValidationException
     */
    protected void validate() throws JPValidationException {
        // overwrite for specific property validation.
    }

    /**
     * Can be overwritten for specific load action. Method is called after loading a property instance.
     */
    protected void loadAction() {
        // overwrite for specific load action.
    }

    protected abstract V getPropertyDefaultValue() throws JPNotAvailableException;

    protected abstract V parse(List<String> arguments) throws Exception;

    /**
     * Method returns the description of the property.
     */
    public abstract String getDescription();

    public enum ValueType {
        PropertyDefault, ApplicationDefault, CommandLine, Runtime
    }
}
