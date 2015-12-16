/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dc.jps.core.helper;

import org.dc.jps.preset.AbstractJPDirectory;
import org.dc.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author mpohling
 */
public class JPBaseDirectory extends AbstractJPDirectory {

	public final static String[] COMMAND_IDENTIFIERS = {"--base"};

	public static FileHandler.ExistenceHandling existenceHandling = FileHandler.ExistenceHandling.Must;
	public static FileHandler.AutoMode autoMode = FileHandler.AutoMode.On;
	
	public JPBaseDirectory() {
		super(COMMAND_IDENTIFIERS, existenceHandling, autoMode);
	}

    @Override
	protected File getPropertyDefaultValue() {
		return new File("/tmp/test/base");
	}

	@Override
	public String getDescription() {
		return "Specifies the database directory.";
	}
}