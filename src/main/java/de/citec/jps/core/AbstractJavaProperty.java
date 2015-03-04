/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.core;

import de.citec.jps.exception.BadArgumentException;
import de.citec.jps.exception.ParsingException;
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
	private V applicationDefaultValue;
	private ValueType valueType, defaultValueType;
	private boolean parsed;

    /**
     * 
     * @param propertyIdentifier
     * @param argumentIdentifiers
     * @deprecated overwrite generateArgumentIdentifiers(); for default argument identifier modification.
     */
    @Deprecated
	public AbstractJavaProperty(String[] propertyIdentifier, String[] argumentIdentifiers) {
        this(propertyIdentifier);
    }
    
	public AbstractJavaProperty(String[] propertyIdentifier) {
		this.propertyIdentifiers = propertyIdentifier;
		this.argumentIdentifiers = generateArgumentIdentifiers();
		this.arguments = new ArrayList<>(argumentIdentifiers.length);
		this.identifier = NOT_IDENTIFIERED;
		this.applicationDefaultValue = null;
		this.defaultValueType = ValueType.PropertyDefault;
		this.setValue(getPropertyDefaultValue(), ValueType.PropertyDefault);
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

	protected void parseArguments() throws ParsingException {
		try {
			setValue(parse(Collections.unmodifiableList(arguments)), ValueType.CommandLine);
		} catch (Exception ex) {
			logger.error("Could not parse argument[" + identifier + "]!");
			logger.info("Right syntax would be: " + getSyntax() + "\n");
			throw new ParsingException("Could not parse argument[" + identifier + "]!", ex);
		}
		parsed = true;
	}

	public V getValue() {
		return value;
	}

	private void setValue(V value, ValueType valueType) {
		this.value = value;
		this.valueType = valueType;
	}

	protected void updateValue() {
		switch (valueType) {
			case PropertyDefault:
				this.value = getPropertyDefaultValue();
				break;
			case ApplicationDefault:
				this.value = applicationDefaultValue;
				break;
			case CommandLine:
				break;
		}
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
		display = display + "]";
		return display;
	}

	protected boolean isIdentifiered() {
		return !identifier.equals("");
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

	public String getExample() {
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

	protected String getOneArgumentResult() throws BadArgumentException {
		checkArgumentCount(1);
		return arguments.get(0);
	}

	public ValueType getValueType() {
		return valueType;
	}

	protected void checkArgumentCount(int size) throws BadArgumentException {
		checkArgumentCount(size, size);
	}

	protected void checkArgumentCount(int min, int max) throws BadArgumentException {
		int size = arguments.size();
		if (size < min) {
			throw new BadArgumentException("Missing property arguments!");
		} else if (size > max) {
			throw new BadArgumentException("To many property arguments!");
		}
	}

	/**
	 * Can be overwritten for value validation. Method is called after parsing.
	 *
	 * @throws Exception
	 */
	protected void validate() throws Exception {
	}

	protected abstract V getPropertyDefaultValue();

	protected abstract V parse(List<String> arguments) throws Exception;

	public abstract String getDescription();
}
