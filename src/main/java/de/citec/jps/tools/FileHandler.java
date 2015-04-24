/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.tools;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * 
 * @author mpohling
 */
public class FileHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

	public enum FileType {

		Directory, File
	};

	public enum AutoMode {

		On, Off
	};

	public enum ExistenceHandling {

		Must, CanExist, MustNot, MustBeNew
	};

	public static File handle(String uri, FileType type, ExistenceHandling existenceHandling, AutoMode autoMode) throws Exception {
		return handle(uri, type, existenceHandling, autoMode, DEFAULT_FILE_CREATOR);
	}

	public static File handle(String uri, FileType type, ExistenceHandling existenceHandling, AutoMode autoMode, FileCreator creator) throws Exception {
		return handle(new File(uri), type, existenceHandling, autoMode);
	}

	public static File handle(File file, FileType type, ExistenceHandling existenceHandling, AutoMode autoMode) throws Exception {
		return handle(file, type, existenceHandling, autoMode, DEFAULT_FILE_CREATOR);
	}

	public static File handle(File file, FileType type, ExistenceHandling existenceHandling, AutoMode autoMode, FileCreator creator) throws Exception {

		if (file.exists()) {
			switch (type) {
				case File:
					if (!file.isFile()) {
						throw new IOException("URI[" + file.getAbsolutePath() + "] is not a " + type.name() + "!");
					}
					break;
				case Directory:
					if (!file.isDirectory()) {
						throw new IOException("URI[" + file.getAbsolutePath() + "] is not a " + type.name() + "!");
					}
					break;
				default:
					throw new AssertionError("Unknown type!");
			}
		}

		LOGGER.debug("analyse "+type+"["+file.getAbsolutePath()+"] exists["+file.exists()+"] "+existenceHandling+ " "+autoMode);

		switch (existenceHandling) {
			case Must:
				if (!file.exists()) {
					if (autoMode == AutoMode.Off) {
						throw new IOException(type.name() + " URI[" + file.getAbsolutePath() + "] does not exits!");
					}
					create(file, type, creator);
				}
				break;
			case MustNot:
				if (file.exists()) {
					if (autoMode == AutoMode.Off) {
						throw new IOException(type.name() + " URI[" + file.getAbsolutePath() + "] exist already!");
					}
					if(!file.canWrite()) {
						throw new IOException("Could not get delete permissions for " + file.getAbsolutePath());
					}
					LOGGER.info("Delete existing " + type.name() + " " + file.getAbsolutePath());
					if (!file.delete()) {
						throw new IOException("Could not delete " + file.getAbsolutePath());
					}
				}
				break;
			case CanExist:
				if (!file.exists()) {
					if (autoMode == AutoMode.On) {
						create(file, type, creator);
					}
				}
				break;
			case MustBeNew:
				if (file.exists()) {
					if (autoMode == AutoMode.Off) {
						throw new IOException(type.name() + " URI[" + file.getAbsolutePath() + "] exist already!");
					}
					if(!file.canWrite()) {
						throw new IOException("Could not get delete permissions for " + file.getAbsolutePath());
					}
					LOGGER.info("Delete existing " + type.name() + " " + file.getAbsolutePath());
                    if(type ==FileType.File) {
                        if (!file.delete()) {
                            throw new IOException("Could not delete " + file.getAbsolutePath());
                        }
                    } else if(type == FileType.Directory) {
                        FileUtils.deleteDirectory(file);
                    }
				}
				create(file, type, creator);
				break;
			default:
				throw new AssertionError("Unknown handling case!");
		}
		return file;
	}

	public static void create(File file, FileType type, FileCreator creator) throws Exception {
		switch (type) {
			case Directory:
				LOGGER.info("Create missing " + type.name() + " " + file.getAbsolutePath());
				if (!file.mkdirs()) {
					throw new IOException("Could not create " + file.getAbsolutePath());
				}
				break;
			case File:
				if (!creator.create(file)) {
					throw new IOException("Could not create " + file.getAbsolutePath());
				}
				break;
			default:
				throw new AssertionError("Handling of " + type.name() + " not defined!");
		}
	}
	public static final FileCreator DEFAULT_FILE_CREATOR = new FileCreator() {

		@Override
		public boolean create(File file) throws IOException {
			return file.createNewFile();
		}
	};

	public interface FileCreator {

		public abstract boolean create(File file) throws Exception;
	}
}
