/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.agai.clparser.command;

import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public class ShowGUIFlag extends AbstractFlag {
	
	private final Logger LOGGER = Logger.getLogger(getClass());
	public final static String[] COMMAND_IDENTIFIERS = {"-g", "--gui"};

	public ShowGUIFlag() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws Exception {
		if(!isIdentifiered()) {
			LOGGER.info("GUI disabled. Set "+COMMAND_IDENTIFIERS[1]+" as program parameter to display the GUI");
		}
	}
	
	@Override
	public String getDescription() {
		return "Displays the Graphic-User-Interface as well.";
	}
}
