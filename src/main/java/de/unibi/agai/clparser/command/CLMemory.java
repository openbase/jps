/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class CLMemory extends AbstractCLString {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public final static String[] COMMAND_IDENTIFIERS = {"-a", "--activememory"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"ActiveMemoryName"};

	public CLMemory() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}
	
	@Override
	protected String getCommandDefaultValue() {
		return "ShortTerm";
	}

	@Override
	public String getDescription() {
		return "Sets the default active memory.";
	}
}
