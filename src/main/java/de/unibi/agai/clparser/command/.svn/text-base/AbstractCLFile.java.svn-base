/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.clparser.exception.ExceptionInfoTag;
import de.unibi.agai.tools.FileHandler;
import de.unibi.agai.tools.FileHandler.AutoMode;
import de.unibi.agai.tools.FileHandler.ExistenceHandling;
import de.unibi.agai.tools.FileHandler.FileType;
import java.io.File;
import java.util.List;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public abstract class AbstractCLFile extends AbstractRunCommand<File> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private final AutoMode autoCreateMode;
	private final ExistenceHandling existenceHandling;
	private FileType type;

	AbstractCLFile(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode, FileType type) {
		this(commandIdentifier, argumentIdentifiers, existenceHandling, autoCreateMode);
		this.type = type;
	}

	public AbstractCLFile(String[] commandIdentifier, String[] argumentIdentifiers, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
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
			throw new ExceptionInfoTag("Use parameter "+getCommandIdentifiers()[0]+" to adjust handling policy for "+getValue(), ex);
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
