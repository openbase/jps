/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dc.jps.preset;

import org.dc.jps.exception.JPValidationException;

/**
 *
 * @author mpohling
 */
public class JPVerbose extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"-v", "--verbose"};

	public JPVerbose() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws JPValidationException {
		if(getValue()) {
			logger.info("Verbose is enabled!");
		}
	}
	
	@Override
	public String getDescription() {
		return "Prints more information during exection to stdout.";
	}
}
