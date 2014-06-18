/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class JPForce extends AbstractJPBoolean {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public final static String[] COMMAND_IDENTIFIERS = {"-f", "--force"};

	public JPForce() {
		super(COMMAND_IDENTIFIERS);
	}

	@Override
	protected void validate() throws Exception {
		if (getValue()) {
			LOGGER.info("Force mode enabled!");
		}
	}

	@Override
	public String getDescription() {
		return "Enables force mode.";
	}
}
