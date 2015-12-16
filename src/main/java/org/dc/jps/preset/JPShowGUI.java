/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dc.jps.preset;

import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPValidationException;

/**
 *
 * @author mpohling
 */
public class JPShowGUI extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"--gui"};

	public JPShowGUI() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws JPValidationException {
		if(!isIdentifiered() && !getValue()) {
			logger.info("GUI is disabled! Set "+COMMAND_IDENTIFIERS[0]+" property to display the GUI.");
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