package org.openbase.jps.preset;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2016 openbase.org
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
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPValidationException;
import static org.openbase.jps.preset.AbstractJPFile.convertIntoValidFileName;
import org.openbase.jps.tools.FileHandler;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
 */
public class JPTmpDirectory extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"--tmp"};

    public JPTmpDirectory() {
        super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
    }

    @Override
    protected File getPropertyDefaultValue() {
        File tmpFolder;
        if (JPService.testMode()) {
            tmpFolder = new File(System.getProperty("java.io.tmpdir", "/tmp") + File.separatorChar + "test" + File.separatorChar + convertIntoValidFileName(JPService.getApplicationName()) + File.separatorChar + convertIntoValidFileName(System.getProperty("user.name", "mrpink")));
        } else {
            tmpFolder = new File(System.getProperty("java.io.tmpdir", "/tmp") + File.separatorChar +  convertIntoValidFileName(JPService.getApplicationName()) + File.separatorChar + convertIntoValidFileName(System.getProperty("user.name", "mrpink")));
        }
        try {
            FileUtils.forceMkdir(tmpFolder);
            return tmpFolder;
        } catch (IOException ex) {
            JPService.printError("Could not create tmp folder :(", ex);
            return new File("/tmp");
        }
    }

    @Override
    public void validate() throws JPValidationException {
        if (JPService.testMode()) {
            setAutoCreateMode(FileHandler.AutoMode.On);
            setExistenceHandling(FileHandler.ExistenceHandling.Must);
        }
        super.validate();
    }

    @Override
    public String getDescription() {
        return "Specifies the application temporary directory which is used for lockfiles and application caches.";
    }
}
