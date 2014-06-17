/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.tools.FileHandler;
import de.unibi.agai.tools.FileHandler.AutoMode;
import de.unibi.agai.tools.FileHandler.ExistenceHandling;
import de.unibi.agai.tools.FileHandler.FileType;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public abstract class AbstractSetFile extends AbstractRunCommand<File> {

	private final Logger LOGGER = Logger.getLogger(getClass());

	private final AutoMode autoCreateMode;
	private final ExistenceHandling existenceHandling;
	private FileType type;

	AbstractSetFile(String[] commandIdentifier, String[] argumentIdentifiers, File[] defaultValues, ExistenceHandling existenceHandling, AutoMode autoCreateMode, FileType type) {
		this(commandIdentifier, argumentIdentifiers, defaultValues, existenceHandling, autoCreateMode);
		this.type = type;
	}

	public AbstractSetFile(String[] commandIdentifier, String[] argumentIdentifiers, File[] defaultValues, ExistenceHandling existenceHandling, AutoMode autoCreateMode) {
		super(commandIdentifier, argumentIdentifiers, defaultValues);
		this.existenceHandling = existenceHandling;
		this.autoCreateMode = autoCreateMode;
		this.type = FileType.File;
	}

	@Override
	protected File parse(String arg) throws Exception {
		return new File(arg);
	}

	@Override
	public void validate() throws Exception {
		FileHandler.handle(values[0], type, existenceHandling, autoCreateMode);
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
		return getClass().getSimpleName()+"[Type:" + type + "|ExistenceHandling:" + existenceHandling + "|AutoMode:" + autoCreateMode + "]";
	}
}
