/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public class SetMemoryCommand extends AbstractRunCommand<String> {

	private final Logger LOGGER = Logger.getLogger(getClass());
	
	public final static String[] COMMAND_IDENTIFIERS = {"-a", "--postureAM"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"ActiveMemoryName"};
	public final static String[] DEFAULT_VALUES = {"ShortTerm"};

	public SetMemoryCommand() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, DEFAULT_VALUES);
	}

	@Override
	protected String parse(String arg) throws Exception {
		return arg;
	}

	@Override
	public String getDescription() {
		return "Set the posture source ActiveMemory.";
	}
}
