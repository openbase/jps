/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author mpohling
 */
public class JPLocalUserPrefix extends AbstractJPDirectory {

	public final static String[] COMMAND_IDENTIFIERS = {"--local-user-prefix"};

	public JPLocalUserPrefix() {
		super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
	}

	@Override
	protected File getPropertyDefaultValue() {
        String localUserPath = System.getProperty("user.home");

        if(localUserPath == null) {
            File executionFolder = new File(".");
			logger.warn("Could not load user prefix! Use execution folder["+executionFolder.getAbsolutePath()+"] instead.");
			return executionFolder;
        }
		return new File(localUserPath);
	}

	@Override
	public String getDescription() {
		return "Overwrites the local user prefix.";
	}
}
