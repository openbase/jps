/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.ValidationException;
import de.citec.jps.tools.FileHandler;
import de.citec.jps.tools.FileHandler.AutoMode;
import de.citec.jps.tools.FileHandler.ExistenceHandling;
import de.citec.jps.tools.FileHandler.FileType;
import java.io.File;
import java.util.List;

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
    public void validate() throws ValidationException {
        try {
            FileHandler.handle(getValue(), type, existenceHandling, autoCreateMode);
        } catch (Exception ex) {
            throw new ValidationException("Could not validate " + getValue() + "!", ex);
        }
    }

    @Override
    protected void setValue(File value, ValueType valueType) {
        File parent = getParentDirectory();
        if (parent == null) {
            super.setValue(value, valueType);
        }
        super.setValue(new File(parent, value.getPath()), valueType);

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
     * Returns the parent dir or null if the no parent exist. Method can be
     * overwritten to defining the parent dir.
     *
     * @return
     */
    public File getParentDirectory() {
        return null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[Type:" + type + "|ExistenceHandling:" + existenceHandling + "|AutoMode:" + autoCreateMode + "]";
    }
}
