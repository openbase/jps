/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.citec.jps.preset;

/**
 *
 * @author mpohling
 */
public class JPShowGUI extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"-g", "--gui"};

	public JPShowGUI() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws Exception {
		if(!isIdentifiered() && !getValue()) {
			logger.info("GUI disabled. Set "+COMMAND_IDENTIFIERS[1]+" as program parameter to display the GUI.");
		}
	}
	
	@Override
	public String getDescription() {
		return "Displays the Graphic-User-Interface as well.";
	}
}
