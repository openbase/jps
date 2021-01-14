/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.preset;

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

import org.openbase.jps.core.AbstractJavaProperty;
import org.openbase.jps.exception.JPServiceException;
import org.openbase.jps.exception.JPValidationException;
import org.openbase.jps.tools.FileHandler;
import org.openbase.jps.tools.FileHandler.AutoMode;
import org.openbase.jps.tools.FileHandler.ExistenceHandling;
import org.openbase.jps.tools.FileHandler.FileType;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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
    }

    AbstractJPFile(final String[] commandIdentifier, final ExistenceHandling existenceHandling, final AutoMode autoCreateMode, final FileType type) {
        this(commandIdentifier, existenceHandling, autoCreateMode);
        this.type = type;
    }

    public static String convertIntoValidFileName(final String filename) {
        return filename.replaceAll("[^0-9a-zA-Z-äöüÄÖÜéàèÉÈß\\.\\-\\_\\[\\]\\#\\$]+", "_");
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
        final File value = getValue();

        if (value == null) {
            throw new JPValidationException(getClass().getSimpleName() + " is not defined but can be set manually with " + getDefaultExample(), getErrorReport());
        }

        try {
            FileHandler.handle(value, type, existenceHandling, autoCreateMode);
        } catch (Exception ex) {
            throw new JPValidationException("Could not handle " + type.name() + "[" + value + "]!", ex);
        }
    }

    @Override
    protected void setValue(File value, ValueType valueType) {
        // if file is already referred absolute than use it and ignore parent directory.
        if (value.isAbsolute()) {
            super.setValue(value, valueType);
            return;
        }

        try {
            File parent = getParentDirectory();

            // if parent file is not defined just use the value directly
            if (parent == null) {
                super.setValue(value, valueType);
                return;
            }

            // build value with parent folder
            super.setValue(new File(parent, value.getPath()), valueType);
        } catch (JPServiceException ex) {
            // ignore value because parent seems to be invalid and value itself is not absolute!
            addErrorReport(new JPServiceException("Parent directory is not valid!", ex), valueType);
        }
    }

    public AutoMode getAutoCreateMode() {
        return autoCreateMode;
    }

    protected void setAutoCreateMode(final AutoMode autoCreateMode) {
        this.autoCreateMode = autoCreateMode;
    }

    public ExistenceHandling getExistenceHandling() {
        return existenceHandling;
    }

    protected void setExistenceHandling(final ExistenceHandling existenceHandling) {
        this.existenceHandling = existenceHandling;
    }

    public FileType getType() {
        return type;
    }

    /**
     * @return the parent dir or null if the no parent exist. Method can be overwritten to defining the parent dir.
     *
     * @throws org.openbase.jps.exception.JPServiceException in case the parent directory is not available.
     */
    public File getParentDirectory() throws JPServiceException {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[Type:" + type + "|ExistenceHandling:" + existenceHandling + "|AutoMode:" + autoCreateMode + "]";
    }
}
