package org.openbase.jps.core.helper;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2021 openbase.org
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
import org.openbase.jps.preset.AbstractJPDirectory;
import org.openbase.jps.tools.FileHandler;
import java.io.File;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.preset.JPTmpDirectory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPBaseDirectory extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"--base"};

    public static FileHandler.ExistenceHandling existenceHandling = FileHandler.ExistenceHandling.Must;
    public static FileHandler.AutoMode autoMode = FileHandler.AutoMode.On;

    public JPBaseDirectory() {
        super(COMMAND_IDENTIFIERS, existenceHandling, autoMode);
    }

    @Override
    protected File getPropertyDefaultValue() throws JPNotAvailableException {
        return new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "base");
    }

    @Override
    public String getDescription() {
        return "Specifies the database directory.";
    }
}
