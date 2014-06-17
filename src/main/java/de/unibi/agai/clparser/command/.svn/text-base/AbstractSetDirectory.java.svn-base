/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.tools.FileHandler;
import de.unibi.agai.tools.FileHandler.AutoMode;
import de.unibi.agai.tools.FileHandler.ExistenceHandling;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public abstract class AbstractSetDirectory extends AbstractSetFile {

	private final Logger LOGGER = Logger.getLogger(getClass());
	
	public AbstractSetDirectory(String[] commandIdentifier, String[] argumentIdentifiers, File[] defaultValues, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier, argumentIdentifiers, defaultValues, existenceHandling ,autoCreateMode, FileHandler.FileType.Directory);
	}
}
