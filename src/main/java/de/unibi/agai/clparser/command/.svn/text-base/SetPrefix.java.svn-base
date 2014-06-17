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
public class SetPrefix extends AbstractCLDirectory {

	private static final Logger LOGGER = Logger.getLogger(SetPrefix.class);

	public final static String[] COMMAND_IDENTIFIERS = {"-p", "--prefix"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PREFIX"};

	public SetPrefix() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
	}
	
	@Override
	protected File getCommandDefaultValue() {
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
