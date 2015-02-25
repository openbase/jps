/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

/**
 *
 * @author mpohling
 */
public class JPMemory extends AbstractJPString {

	public final static String[] COMMAND_IDENTIFIERS = {"-a", "--activememory"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"ActiveMemoryName"};

	public JPMemory() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}
	
	@Override
	protected String getPropertyDefaultValue() {
		return "ShortTerm";
	}

	@Override
	public String getDescription() {
		return "Sets the default active memory.";
	}
}
