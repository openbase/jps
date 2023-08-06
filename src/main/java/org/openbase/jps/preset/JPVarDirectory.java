package org.openbase.jps.preset;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2023 openbase.org
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
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.exception.JPValidationException;
import org.openbase.jps.tools.FileHandler;
import org.openbase.jps.tools.FileHandler.ExistenceHandling;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPVarDirectory extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"--var"};

    public JPVarDirectory() {
        super(COMMAND_IDENTIFIERS, ExistenceHandling.CanExist, FileHandler.AutoMode.Off);
        registerDependingProperty(JPPrefix.class);
    }

    @Override
    public File getParentDirectory() throws JPNotAvailableException {
        return JPService.getProperty(JPPrefix.class).getValue();
    }

    @Override
    protected File getPropertyDefaultValue() {
        return new File("var");
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
        return "Specifies the global system var directory which is used for storing variable application data like databases or models.";
    }
}
