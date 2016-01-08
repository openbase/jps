/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPNotAvailableException;
import org.dc.jps.tools.FileHandler;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author mpohling
 */
public class JPPrefix extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"-p", "--prefix"};

    public JPPrefix() {
        super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
    }

    @Override
    protected File getPropertyDefaultValue() throws JPNotAvailableException {
        if (JPService.testMode()) {
            File tmpFolder = new File(System.getProperty("java.io.tmpdir", "/tmp") + "/" + convertIntoValidFileName(System.getProperty("user.name", "mrpink")));
            try {
                FileUtils.forceMkdir(tmpFolder);
                return tmpFolder;
            } catch (IOException ex) {
                JPService.printError("Could not create tmp folder :(", ex);
            }
        }

        String globalPrefix = System.getenv("prefix");
        if (globalPrefix == null) {
            File localUserPrefix;
            localUserPrefix = JPService.getProperty(JPLocalUserPrefix.class).getValue();
            logger.warn("Could not load global prefix! Use local user Prefix[" + localUserPrefix.getAbsolutePath() + "] instead.");
            return localUserPrefix;
        }
        return new File(globalPrefix);
    }

    @Override
    public String getDescription() {
        return "Set the application prefix, which is used for accessing configurations and storing temporally data.";
    }
}
