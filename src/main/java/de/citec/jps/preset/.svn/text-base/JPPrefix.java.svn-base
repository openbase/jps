/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.tools.FileHandler;
import java.io.File;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class SetPrefix extends AbstractCLDirectory {

	private static final Logger LOGGER = LoggerFactory.getLogger(SetPrefix.class);

	public final static String[] COMMAND_IDENTIFIERS = {"-p", "--prefix"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PREFIX"};

	public SetPrefix() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
	}
	
	@Override
	protected File getCommandDefaultValue() {
		String globalPrefix = System.getenv("prefix");
		if(globalPrefix == null) {
			File executionFolder = new File(".");
			LOGGER.warn("Could not load global prefix! Use execution folder["+executionFolder.getAbsolutePath()+"] instead.");
			return executionFolder;
		}
		return new File(globalPrefix);
	}

	@Override
	public String getDescription() {
		return "Set the prefix.";
	}
}
