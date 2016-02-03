/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2016 DivineCooperation
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
