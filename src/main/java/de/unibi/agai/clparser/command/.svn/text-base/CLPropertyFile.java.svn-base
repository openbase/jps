/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.tools.FileHandler;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class CLPropertyFile extends AbstractCLFile {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public final static String[] COMMAND_IDENTIFIERS = {"--properties"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"FILE"};

	public static FileHandler.ExistenceHandling existenceHandling = FileHandler.ExistenceHandling.CanExist;
	public static FileHandler.AutoMode autoMode = FileHandler.AutoMode.On;
	
	public CLPropertyFile() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, existenceHandling, autoMode);
	}

	@Override
	protected File getCommandDefaultValue() {
		return new File(System.getProperty("user.home")+System.getProperty("path.separator")+".clparser"+System.getProperty("path.separator")+CLParser.getProgramName()+".properties");
	}

	@Override
	public String getDescription() {
		return "Set the neutral posture URI. URI is absolute and prefix independent handled.";
	}
}
