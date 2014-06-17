/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser;

import de.unibi.agai.clparser.exception.BadArgumentException;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public abstract class AbstractRunCommand<V> implements Comparable<AbstractRunCommand> { // <Value,ActionClass>

	private final Logger LOGGER = Logger.getLogger(getClass());

	protected String identifier = "";
	protected final String[] commandIdentifiers;
	protected final String[] argumentIdentifiers;
	protected final String[] arguments;
	protected final V[] values;
	protected V[] defaultValues;
	private int argCounter;
	private boolean parsed;

	public AbstractRunCommand(String[] commandIdentifier, String[] argumentIdentifiers, V[] defaultValues) {
		this.commandIdentifiers = commandIdentifier;
		this.argumentIdentifiers = argumentIdentifiers;
		this.arguments = new String[argumentIdentifiers.length];
		this.defaultValues = defaultValues;
		this.values = defaultValues.clone();
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
		argCounter = 0;
		parsed = false;
	}

	protected void addArgument(String arg) {
		if (argCounter >= argumentIdentifiers.length) {
			LOGGER.error("There are to many arguments for command " + identifier);
			return;
		}
		arguments[argCounter] = arg;
		argCounter++;
	}

	protected boolean parseArguments() {
		try {
			for (int i = 0; i < values.length; i++) {
				values[i] = parse(arguments[i]);
			}
		} catch (BadArgumentException ex) {
			LOGGER.error("Could not handle at least one " + identifier + " argument!", ex);
			return false;
		} catch (Exception ex) {
			LOGGER.error("Could not parse " + identifier + " arguments!", ex);
			LOGGER.info("Right syntax would be: " + getSyntax() + "\n");
			return false;
		}

		parsed = true;
		return true;
	}

	public V[] getValues() {
		return values;
	}

	public V getValue() {
		if (values.length == 0) {
			return ((V) new Boolean(isIdentifiered()));
		}
		return values[0];
	}

	/**
	 * This service method is just for debug and test issues. Usage is exclusive
	 * for JUnitTests!
	 *
	 * @return
	 */
	public void setValue(V value, int index) {
		assert(index >= 0  && index < values.length);
		assert value != null;
		values[index] = value;
	}

	/**
	 * This service method is just for debug and test issues. Usage is exclusive
	 * for JUnitTests!
	 *
	 */
	public void setValues(V[] values) {
		assert(values.length != 0);
		System.arraycopy(values, 0, this.values, 0, this.values.length);
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

	public V[] getDefaultValues() {
		return defaultValues;
	}

	protected String[] getCommandIdentifiers() {
		return commandIdentifiers;
	}

	protected String[] getArgumentIdentifiers() {
		return argumentIdentifiers;
	}

	public String getExample() {
		String example = commandIdentifiers[0];
		for (V defaultVaule : defaultValues) {
			example += " " + defaultVaule;
		}
		return example;
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

	public void activateDefault() {
		parsed = true;
	}

	@Override
	public int compareTo(AbstractRunCommand o) {
		return commandIdentifiers[0].compareTo(o.commandIdentifiers[0]);
	}

	/**
	 * Can be overwritten for special actions. Method is called after parsing.
	 *
	 * @throws Exception
	 *
	 * @deprecated Method not called anymore.
	 */
//	@Deprecated
//	protected void action() throws Exception {
//	}

	/**
	 * Can be overwritten for value validation. Method is called after parsing.
	 *
	 * @throws Exception
	 */
	protected void validate() throws Exception {
	}

	protected abstract V parse(String arg) throws Exception;

	public abstract String getDescription();
}
