/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.tools.FileHandler;
import java.io.File;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class JPPrefix extends AbstractJPDirectory {

	private static final Logger LOGGER = LoggerFactory.getLogger(JPPrefix.class);

	public final static String[] COMMAND_IDENTIFIERS = {"-p", "--prefix"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PREFIX"};

	public JPPrefix() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
	}
	
	@Override
	protected File getPropertyDefaultValue() {
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
