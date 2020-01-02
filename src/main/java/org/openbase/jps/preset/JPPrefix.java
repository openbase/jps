/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2020 openbase.org
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

import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.tools.FileHandler;

import java.io.File;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPPrefix extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"-p", "--prefix"};

    public JPPrefix() {
        super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.Off);
        registerDependingProperty(JPSystemDirectory.class);
    }

    @Override
    protected File getPropertyDefaultValue() throws JPNotAvailableException {
        if (JPService.testMode()) {
            return JPService.getProperty(JPTmpDirectory.class).getValue();
        }

        // load prefix via system variable
        String globalPrefix = System.getenv("prefix");
        if (globalPrefix != null) {
            return new File(globalPrefix);
        }

        // load prefix via system variable
        globalPrefix = System.getenv("PREFIX");
        if (globalPrefix != null) {
            return new File(globalPrefix);
        }

        // use global usr folder as prefix
        return JPService.getProperty(JPSystemDirectory.class).getValue();
    }

    @Override
    public String getDescription() {
        return "Set the application prefix, which is used for accessing binaries, shared data and templates.";
    }
}
