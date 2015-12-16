/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dc.jps.core.helper;

import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPServiceException;
import org.dc.jps.preset.AbstractJPDirectory;
import org.dc.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author mpohling
 */
public class JPChildDirectory extends AbstractJPDirectory {

	public final static String[] COMMAND_IDENTIFIERS = {"--child"};

	public JPChildDirectory() {
		super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.On);
	}

    @Override
    public File getParentDirectory() throws JPServiceException {
        return JPService.getProperty(JPBaseDirectory.class).getValue();
    }

	@Override
	protected File getPropertyDefaultValue() {
		return new File("child");
	}

	@Override
	public String getDescription() {
		return "Test database location.";
	}
}