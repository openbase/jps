/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.tools.FileHandler;
import de.unibi.agai.tools.FileHandler.AutoMode;
import de.unibi.agai.tools.FileHandler.ExistenceHandling;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public abstract class AbstractCLDirectory extends AbstractCLFile {

	private final Logger LOGGER = Logger.getLogger(getClass());
	
	public AbstractCLDirectory(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier, argumentIdentifiers, existenceHandling ,autoCreateMode, FileHandler.FileType.Directory);
	}
}
