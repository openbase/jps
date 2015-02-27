/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.ExceptionInfoTag;
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

	private final AutoMode autoCreateMode;
	private final ExistenceHandling existenceHandling;
	private FileType type;

	AbstractJPFile(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode, FileType type) {
		this(commandIdentifier, argumentIdentifiers, existenceHandling, autoCreateMode);
		this.type = type;
	}

	public AbstractJPFile(String[] commandIdentifier, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
        this(commandIdentifier, ARGUMENT_IDENTIFIERS, existenceHandling, autoCreateMode);
    }
    
	public AbstractJPFile(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier, argumentIdentifiers);
		this.existenceHandling = existenceHandling;
		this.autoCreateMode = autoCreateMode;
		this.type = FileType.File;
	}

	@Override
	protected File parse(List<String> arguments) throws Exception {
		return new File(getOneArgumentResult());
	}

	@Override
	public void validate() throws Exception {
		try {
			FileHandler.handle(getValue(), type, existenceHandling, autoCreateMode);
		} catch (Exception ex) {
			throw new ExceptionInfoTag("Use parameter "+getPropertyIdentifiers()[0]+" to adjust handling policy for "+getValue(), ex);
		}
	}

	public AutoMode getAutoCreateMode() {
		return autoCreateMode;
	}

	public ExistenceHandling getExistenceHandling() {
		return existenceHandling;
	}

	public FileType getType() {
		return type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[Type:" + type + "|ExistenceHandling:" + existenceHandling + "|AutoMode:" + autoCreateMode + "]";
	}
}
