/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.tools.FileHandler;
import de.unibi.agai.tools.FileHandler.AutoMode;
import de.unibi.agai.tools.FileHandler.ExistenceHandling;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public abstract class AbstractCLDirectory extends AbstractCLFile {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public AbstractCLDirectory(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier, argumentIdentifiers, existenceHandling ,autoCreateMode, FileHandler.FileType.Directory);
	}
}
