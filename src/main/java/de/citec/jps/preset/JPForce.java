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
public class JPForce extends AbstractJPBoolean {

	public final static String[] COMMAND_IDENTIFIERS = {"-f", "--force"};

	public JPForce() {
		super(COMMAND_IDENTIFIERS);
	}

	@Override
	protected void validate() throws JPValidationException {
		if (getValue()) {
			logger.warn("Force mode enabled!");
		}
	}

	@Override
	public String getDescription() {
		return "Enables force mode.";
	}
}
