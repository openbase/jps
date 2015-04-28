/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.citec.jps.preset;

import de.citec.jps.core.JPService;
import de.citec.jps.exception.ValidationException;

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
	protected void validate() throws ValidationException {
		if(!isIdentifiered() && !getValue()) {
			logger.info("GUI is disabled! Set "+COMMAND_IDENTIFIERS[1]+" property to display the GUI.");
		}
	}

    @Override
    protected Boolean getPropertyDefaultValue() {
        return true;
    }
	
	@Override
	public String getDescription() {
		return "Displays the Graphic-User-Interface of "+JPService.getApplicationName();
	}
}
