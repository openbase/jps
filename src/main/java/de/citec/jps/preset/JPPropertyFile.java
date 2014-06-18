/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.JPService;
import de.citec.jps.tools.FileHandler;
import java.io.File;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class JPPropertyFile extends AbstractJPFile {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public final static String[] COMMAND_IDENTIFIERS = {"--properties"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"FILE"};

	public static FileHandler.ExistenceHandling existenceHandling = FileHandler.ExistenceHandling.CanExist;
	public static FileHandler.AutoMode autoMode = FileHandler.AutoMode.On;
	
	public JPPropertyFile() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, existenceHandling, autoMode);
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(System.getProperty("user.home")+System.getProperty("path.separator")+".clparser"+System.getProperty("path.separator")+JPService.getApplicationName()+".properties");
	}

	@Override
	public String getDescription() {
		return "Set the neutral posture URI. URI is absolute and prefix independent handled.";
	}
}
