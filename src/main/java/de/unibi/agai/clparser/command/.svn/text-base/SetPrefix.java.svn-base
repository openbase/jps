/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.tools.FileHandler;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public class SetPrefix extends AbstractSetDirectory {

	private static final Logger LOGGER = Logger.getLogger(SetPrefix.class);

	public final static String[] COMMAND_IDENTIFIERS = {"-p", "--prefix"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PREFIX"};
	public final static File[] DEFAULT_VALUES = {loadGlobalPrefix()};

	public SetPrefix() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, DEFAULT_VALUES, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
	}
	
	public static File loadGlobalPrefix() {
		String globalPrefix = System.getenv("prefix");
		if(globalPrefix == null) {
			LOGGER.warn("Could not load global prefix! Use execution folder instead.");
			return new File(".");
		}
		return new File(globalPrefix);
		
	}

	@Override
	public String getDescription() {
		return "Set the prefix.";
	}
}
