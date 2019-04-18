package org.openbase.jps.preset;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2019 openbase.org
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

import org.apache.commons.lang3.SystemUtils;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.exception.JPValidationException;
import org.openbase.jps.tools.FileHandler;

import java.io.File;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPSystemDirectory extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"--usr"};

    public JPSystemDirectory() {
        super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
    }

    @Override
    public File getParentDirectory() throws JPNotAvailableException {

        // on windows we return the windows dir.
        if(SystemUtils.IS_OS_WINDOWS) {
            return new File(System.getenv("WINDIR"));
        }

        // on unix based systems we return the root folder.
        return new File("/");
    }

    @Override
    protected File getPropertyDefaultValue() {

        // on windows we return the system32 folder.
        if(SystemUtils.IS_OS_WINDOWS) {
            return new File("system32");
        }

        // on unix based systems we return the usr folder.
        return new File("usr");
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
        return "Specifies the global system directory which is used to lookup static resources like databases, models, templates or images.";
    }
}
