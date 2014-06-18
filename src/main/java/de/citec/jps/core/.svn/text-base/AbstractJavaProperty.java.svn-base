/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser;

import de.unibi.agai.clparser.exception.BadArgumentException;
import de.unibi.agai.clparser.exception.ParsingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public abstract class AbstractRunCommand<V> implements Comparable<AbstractRunCommand> { // <Value,ActionClass>

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String NOT_IDENTIFIERED = "";

	public enum ValueType {

		CommandDefault, ApplicationDefault, CLParsed
	};
	protected String identifier;
	protected final String[] commandIdentifiers;
	protected final String[] argumentIdentifiers;
	protected final List<String> arguments;
	private V value;
	private V applicationDefaultValue;
	private ValueType valueType, defaultValueType;
	private boolean parsed;

	public AbstractRunCommand(String[] commandIdentifier, String[] argumentIdentifiers) {
		this.commandIdentifiers = commandIdentifier;
		this.argumentIdentifiers = argumentIdentifiers;
		this.arguments = new ArrayList<String>(argumentIdentifiers.length);
		this.identifier = NOT_IDENTIFIERED;
		this.applicationDefaultValue = null;
		this.defaultValueType = ValueType.CommandDefault;
		this.setValue(getCommandDefaultValue(), ValueType.CommandDefault);
		this.reset();
	}

	public boolean match(String commandIdentifier) {
		for (String command : commandIdentifiers) {
			if (command.equals(commandIdentifier)) {
				identifier = commandIdentifier;
				return true;
			}
		}
		return false;
	}

	public void reset() {
		arguments.clear();
		parsed = false;
	}

	protected void addArgument(String arg) {
		arguments.add(arg);
	}

	protected void parseArguments() throws ParsingException {
		try {
			setValue(parse(Collections.unmodifiableList(arguments)), ValueType.CLParsed);
		} catch (Exception ex) {
			LOGGER.error("Could not parse argument[" + identifier + "]!");
			LOGGER.info("Right syntax would be: " + getSyntax() + "\n");
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

	public void updateValue() {
		switch(valueType) {
			case CommandDefault:
				this.value = getCommandDefaultValue();
				break;
			case ApplicationDefault:
				this.value = applicationDefaultValue;
				break;
			case CLParsed:
				break;
		}
	}

	public void overwriteDefaultValue(V defaultValue) {
		this.applicationDefaultValue = defaultValue;
		this.defaultValueType = ValueType.ApplicationDefault;
		if (valueType.equals(ValueType.CommandDefault)) {
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
		switch(defaultValueType) {
			case ApplicationDefault:
				return applicationDefaultValue;
			default:
				return getCommandDefaultValue();
		}
	}

	protected String[] getCommandIdentifiers() {
		return commandIdentifiers;
	}

	protected String[] getArgumentIdentifiers() {
		return argumentIdentifiers;
	}

	public String getExample() {
		return commandIdentifiers[0] + " " + getDefaultValue();
	}

	public String getSyntax() {
		String syntax = "";
		for (int i = 0; i < commandIdentifiers.length; i++) {
			syntax += commandIdentifiers[i];
			for (String argumentIdentifier : argumentIdentifiers) {
				syntax += " '" + argumentIdentifier + "'";
			}
			if (i < commandIdentifiers.length - 1) {
				syntax += " | ";
			}
		}
		return syntax;
	}

	public boolean isParsed() {
		return parsed;
	}
	
	@Override
	public int compareTo(AbstractRunCommand o) {
		return commandIdentifiers[0].compareTo(o.commandIdentifiers[0]);
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
			throw new BadArgumentException("Missing command arguments!");
		} else if (size > max) {
			throw new BadArgumentException("To many command arguments!");
		}
	}

	/**
	 * Can be overwritten for value validation. Method is called after parsing.
	 *
	 * @throws Exception
	 */
	protected void validate() throws Exception {
	}

	protected abstract V getCommandDefaultValue();

	protected abstract V parse(List<String> arguments) throws Exception;

	public abstract String getDescription();
}
