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
public class JPDebugMode extends AbstractJPBoolean {
	

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public final static String[] COMMAND_IDENTIFIERS = {"-d", "--DebugMode"};

	public JPDebugMode() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws Exception {
		if(getValue()) {
			LOGGER.info("Debug mode enabled!");
		}
	}
	
	@Override
	public String getDescription() {
		return "Enables the debug mode.";
	}
}
