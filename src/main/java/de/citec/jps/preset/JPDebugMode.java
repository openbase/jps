/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.citec.jps.preset;

/**
 *
 * @author mpohling
 */
public class JPDebugMode extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"-d", "--DebugMode"};

	public JPDebugMode() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws Exception {
		if(getValue()) {
			logger.info("Debug mode enabled!");
		}
	}
	
	@Override
	public String getDescription() {
		return "Enables the debug mode.";
	}
}
