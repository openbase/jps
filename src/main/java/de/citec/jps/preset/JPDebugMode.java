/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.citec.jps.preset;

import de.citec.jps.exception.JPValidationException;

/**
 *
 * @author mpohling
 */
public class JPDebugMode extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"-d", "--debug"};

	public JPDebugMode() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws JPValidationException {
		if(getValue()) {
			logger.info("Debug mode enabled!");
		}
	}
	
	@Override
	public String getDescription() {
		return "Enables the debug mode.";
	}
}
