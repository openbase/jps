/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.exception.ValidationException;

/**
 *
 * @author mpohling
 */
public class JPTestMode extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"--test"};

	public JPTestMode() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws ValidationException {
		if(getValue()) {
			logger.info("Test mode enabled!");
		}
	}
	
	@Override
	public String getDescription() {
		return "Enables the test mode used by junit tests.";
	}
}