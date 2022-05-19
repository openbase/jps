package org.openbase.jps.preset;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2022 openbase.org
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
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPValidationException;
import org.openbase.jps.tools.FileHandler;
import org.openbase.jps.tools.FileHandler.AutoMode;
import org.openbase.jps.tools.FileHandler.ExistenceHandling;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPTmpDirectory extends AbstractJPDirectory {

    public final static String SYSTEM_TMP_DIRECTORY = System.getProperty("java.io.tmpdir", "/tmp");
    public final static String TEST_DIRECTORY = "test";
    public final static String[] COMMAND_IDENTIFIERS = {"--tmp"};
    private final static Object deletionLock = new Object();
    
    private File tmpDefaultDirectory;

    public JPTmpDirectory() {
        super(COMMAND_IDENTIFIERS, ExistenceHandling.Must, AutoMode.Off);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                synchronized (deletionLock) {
                    try {

                        // just exit if not yet available
                        if (getValue() == null) {
                            return;
                        }

                        // cleanup tmp folder
                        if (getValue().exists()) {
                            FileUtils.deleteQuietly(getValue());
                        }
                        // cleanup parent folder if structure is known
                        if (getValue().getAbsolutePath().equals(tmpDefaultDirectory.getAbsolutePath())) {
                            if (JPService.testMode()) {
                                deleteDirectoryIfEmpty(new File(SYSTEM_TMP_DIRECTORY + File.separatorChar + TEST_DIRECTORY + File.separatorChar + convertIntoValidFileName(JPService.getApplicationName())));
                                deleteDirectoryIfEmpty(new File(SYSTEM_TMP_DIRECTORY + File.separatorChar + TEST_DIRECTORY));
                            } else {
                                deleteDirectoryIfEmpty(new File(SYSTEM_TMP_DIRECTORY + File.separatorChar + convertIntoValidFileName(JPService.getApplicationName())));
                            }
                        }

                    } catch (IllegalArgumentException ex) {
                        JPService.printError("Could not delete tmp directory!", ex);
                    }
                }
            }
        });
    }

    private void deleteDirectoryIfEmpty(final File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }

        // recursive deletion of empty folders.
        final File[] subfiles = file.listFiles();
        if(subfiles != null && subfiles.length > 0) {
            for (File child : Objects.requireNonNull(subfiles)) {
                deleteDirectoryIfEmpty(child);
            }
        }

        try {
            if (FileUtils.isEmptyDirectory(file)) {
                FileUtils.deleteQuietly(file);
            }
        } catch (IOException ex) {
            // do nothing if empty check fails.
        }
    }

    @Override
    protected File getPropertyDefaultValue() {
        File tmpFolder;
        if (JPService.testMode()) {
            tmpFolder = new File(SYSTEM_TMP_DIRECTORY + File.separatorChar + TEST_DIRECTORY + File.separatorChar + convertIntoValidFileName(JPService.getApplicationName()) + File.separatorChar + convertIntoValidFileName(System.getProperty("user.name", "mrpink")));
        } else {
            tmpFolder = new File(SYSTEM_TMP_DIRECTORY + File.separatorChar + convertIntoValidFileName(JPService.getApplicationName()) + File.separatorChar + convertIntoValidFileName(System.getProperty("user.name", "mrpink")));
        }
        try {
            FileUtils.forceMkdir(tmpFolder);
            tmpDefaultDirectory = tmpFolder;
            return tmpFolder;
        } catch (IOException ex) {
            JPService.printError("Could not create tmp folder :(", ex);
            tmpDefaultDirectory = tmpFolder;
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
