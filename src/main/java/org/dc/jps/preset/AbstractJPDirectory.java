/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.tools.FileHandler;
import org.dc.jps.tools.FileHandler.AutoMode;
import org.dc.jps.tools.FileHandler.ExistenceHandling;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPDirectory extends AbstractJPFile {

    private static final String[] ARGUMENT_IDENTIFIERS = {"DIRECTORY"};


    public AbstractJPDirectory(String[] commandIdentifier, final ExistenceHandling existenceHandling, final AutoMode autoCreateMode) {
        super(commandIdentifier, existenceHandling, autoCreateMode, FileHandler.FileType.Directory);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }
}
