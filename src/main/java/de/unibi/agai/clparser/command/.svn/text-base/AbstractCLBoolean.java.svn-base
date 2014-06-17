/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.clparser.exception.BadArgumentException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author divine
 */
public abstract class AbstractCLBoolean extends AbstractRunCommand<Boolean> {

	private final Logger LOGGER = Logger.getLogger(getClass());
	public final static String[] ARGUMENT_IDENTIFIERS = {"BOOLEAN"};

	public AbstractCLBoolean(String[] COMMAND_IDENTIFIERS) {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}

	@Override
	protected Boolean parse(List<String> arguments) throws BadArgumentException {
		checkArgumentCount(0, 1);
		if (arguments.isEmpty()) { // parse as flag
			return true;
		}
		return Boolean.parseBoolean(arguments.get(0)); // parse as argument
	}
	
	@Override
	protected Boolean getCommandDefaultValue() {
		return false;
	}
}
