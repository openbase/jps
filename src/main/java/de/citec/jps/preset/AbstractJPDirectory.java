/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.tools.FileHandler;
import de.citec.jps.tools.FileHandler.AutoMode;
import de.citec.jps.tools.FileHandler.ExistenceHandling;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPDirectory extends AbstractJPFile {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public AbstractJPDirectory(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier, argumentIdentifiers, existenceHandling ,autoCreateMode, FileHandler.FileType.Directory);
	}
}
