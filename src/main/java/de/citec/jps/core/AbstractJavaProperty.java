/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.core;

import de.citec.jps.exception.JPBadArgumentException;
import de.citec.jps.exception.JPParsingException;
import de.citec.jps.exception.JPValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJavaProperty<V> implements Comparable<AbstractJavaProperty> { // <Value,ActionClass>

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String NOT_IDENTIFIERED = "";

	public enum ValueType {

		PropertyDefault, ApplicationDefault, CommandLine
	};
	protected String identifier;
	protected final String[] propertyIdentifiers;
	protected final String[] argumentIdentifiers;
	protected final List<String> arguments;
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
			logger.info("Right syntax would be: " + getSyntax() + "\n");
			throw new JPParsingException("Could not parse argument[" + identifier + "]!", ex);
		}
		parsed = true;
	}

	public V getValue() {
		return value;
	}

	protected void setValue(V value, ValueType valueType) {
		this.value = value;
		this.valueType = valueType;
	}

	protected void updateValue() {
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

	public V getDefaultValue() {
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
		return propertyIdentifiers[0] + " " + getDefaultValue();
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

	protected boolean isParsed() {
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
	 *
	 */
    protected void loadAction() {
        // overwrite for specific load action.
    }

	protected abstract V getPropertyDefaultValue();

	protected abstract V parse(List<String> arguments) throws Exception;
    
    

	public abstract String getDescription();
}
