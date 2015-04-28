/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.ExceptionInfoTag;
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

    /**
     *
     * @param commandIdentifier
     * @param argumentIdentifiers
     * @param existenceHandling
     * @param autoCreateMode
     * @deprecated overwrite generateArgumentIdentifiers(); for default argument identifier modification.
     */
    @Deprecated
	public AbstractJPFile(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
        this(commandIdentifier, existenceHandling, autoCreateMode);
    }
    
	public AbstractJPFile(String[] commandIdentifier, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier);
		this.existenceHandling = existenceHandling;
		this.autoCreateMode = autoCreateMode;
		this.type = FileType.File;
	}
    
	AbstractJPFile(String[] commandIdentifier, ExistenceHandling existenceHandling, AutoMode autoCreateMode, FileType type) {
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
			throw new ValidationException("Could not validate "+getValue()+"!", ex);
		}
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[Type:" + type + "|ExistenceHandling:" + existenceHandling + "|AutoMode:" + autoCreateMode + "]";
	}
}
