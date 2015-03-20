/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.JPService;
import de.citec.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author mpohling
 */
public class JPPropertyFile extends AbstractJPFile {

	public final static String[] COMMAND_IDENTIFIERS = {"--properties"};

	public static FileHandler.ExistenceHandling existenceHandling = FileHandler.ExistenceHandling.CanExist;
	public static FileHandler.AutoMode autoMode = FileHandler.AutoMode.On;
	
	public JPPropertyFile() {
		super(COMMAND_IDENTIFIERS, existenceHandling, autoMode);
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(System.getProperty("user.home")+System.getProperty("path.separator")+".JPService"+System.getProperty("path.separator")+JPService.getApplicationName()+".properties");
	}

	@Override
	public String getDescription() {
		return "Set the neutral posture URI. URI is absolute and prefix independent handled.";
	}
}
