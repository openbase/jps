/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

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
	protected void validate() throws Exception {
		if (getValue()) {
			logger.info("Force mode enabled!");
		}
	}

	@Override
	public String getDescription() {
		return "Enables force mode.";
	}
}
