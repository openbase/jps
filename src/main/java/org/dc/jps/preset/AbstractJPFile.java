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

import java.io.File;
import java.util.List;
import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPServiceException;
import org.dc.jps.exception.JPValidationException;
import org.dc.jps.tools.FileHandler;
import org.dc.jps.tools.FileHandler.AutoMode;
import org.dc.jps.tools.FileHandler.ExistenceHandling;
import org.dc.jps.tools.FileHandler.FileType;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPFile extends AbstractJavaProperty<File> {

    private static final String[] ARGUMENT_IDENTIFIERS = {"FILE"};

    private AutoMode autoCreateMode;
    private ExistenceHandling existenceHandling;
    private FileType type;

    public AbstractJPFile(final String[] commandIdentifier, final ExistenceHandling existenceHandling, final AutoMode autoCreateMode) {
        super(commandIdentifier);
        this.existenceHandling = existenceHandling;
        this.autoCreateMode = autoCreateMode;
        this.type = FileType.File;

        // preload partent folder properties.
        try {
            getParentDirectory();
        } catch (Exception ex) {
            logger.warn("Could not preload parent directory!");
        }
    }

    AbstractJPFile(final String[] commandIdentifier, final ExistenceHandling existenceHandling, final AutoMode autoCreateMode, final FileType type) {
        this(commandIdentifier, existenceHandling, autoCreateMode);
        this.type = type;
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected File parse(List<String> arguments) throws Exception {
        return new File(getOneArgumentResult());
    }

    @Override
    public void validate() throws JPValidationException {
        try {
            FileHandler.handle(getValue(), type, existenceHandling, autoCreateMode);
        } catch (Exception ex) {
            throw new JPValidationException("Could not validate " + getValue() + "!", ex);
        }
    }

    @Override
    protected void setValue(File value, ValueType valueType) {
        try {
            File parent = getParentDirectory();
            if (parent != null && !value.isAbsolute()) {
                super.setValue(new File(parent, value.getPath()), valueType);
                return;
            }
        } catch (JPServiceException ex) {
            JPService.printError(new JPServiceException("Could not load parent directory of Property[" + getClass().getSimpleName() + "]!", ex));
        }

        super.setValue(value, valueType);
    }

    public AutoMode getAutoCreateMode() {
        return autoCreateMode;
    }

    public ExistenceHandling getExistenceHandling() {
        return existenceHandling;
    }

    protected void setAutoCreateMode(final AutoMode autoCreateMode) {
        this.autoCreateMode = autoCreateMode;
    }

    protected void setExistenceHandling(final ExistenceHandling existenceHandling) {
        this.existenceHandling = existenceHandling;
    }

    public FileType getType() {
        return type;
    }

    /**
     * Returns the parent dir or null if the no parent exist. Method can be overwritten to defining the parent dir.
     *
     * @return
     * @throws org.dc.jps.exception.JPServiceException
     */
    public File getParentDirectory() throws JPServiceException {
        return null;
    }

    public static String convertIntoValidFileName(final String filename) {
        return filename.replaceAll("[^0-9a-zA-Z-äöüÄÖÜéàèÉÈßÄ\\.\\-\\_\\[\\]\\#\\$]+", "_");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[Type:" + type + "|ExistenceHandling:" + existenceHandling + "|AutoMode:" + autoCreateMode + "]";
    }
}
