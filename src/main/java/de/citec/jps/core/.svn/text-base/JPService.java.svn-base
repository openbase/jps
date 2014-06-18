/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser;

import de.unibi.agai.clparser.command.CLPrintHelp;
import de.unibi.agai.clparser.command.CLPropertyFile;
import de.unibi.agai.clparser.exception.BadArgumentException;
import de.unibi.agai.clparser.exception.CLParserException;
import de.unibi.agai.clparser.exception.CLParserRuntimeException;
import de.unibi.agai.clparser.exception.CommandInitializationException;
import de.unibi.agai.clparser.exception.ParsingException;
import de.unibi.agai.clparser.exception.ValidationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command Line Parser, this is the central lib controller used to initialize
 * and manage all command line values.
 *
 * @author Divine <DivineThreepwood@gmail.com>
 *
 *
 * CLParser Library can be used for managing the argument handling of a specific
 * programm. The argument definition is realized by registrating classes which
 * extends the AbstractRunCommand. Common argument types are supported by the
 * basic commands (e.g. Integer, Boolean, String...).
 *
 * The library supports the generation of an command overview page. These is
 * reachable with the preregistrated --help or -h command.
 *
 *
 */
public class CLParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(CLParser.class);
	private static Set<Class<? extends AbstractRunCommand>> registeredCommandClasses = new HashSet<Class<? extends AbstractRunCommand>>();
	private static HashMap<Class<? extends AbstractRunCommand>, AbstractRunCommand> runCommands = new HashMap<Class<? extends AbstractRunCommand>, AbstractRunCommand>();
	private static HashMap<Class<? extends AbstractRunCommand>, Object> overwrittenDefaultValueMap = new HashMap<Class<? extends AbstractRunCommand>, Object>();
	private static String programName = "";
	private static boolean argumentsAnalyzed = false;

	static {
		registerCommand(CLPrintHelp.class);
	}

	/**
	 * Set the application name. The name is displayed in the help screen in the
	 * command usage example.
	 *
	 * @param name the application name
	 */
	public static void setProgramName(String name) {
		programName = name;
	}

	/**
	 * @deprecated use registerCommand(Class<? extends AbstractRunCommand>
	 * commandClass) instead!
	 * @param command
	 */
	public static void addCommand(AbstractRunCommand command) {
		registerCommand(command.getClass());
	}

	/**
	 * Register the given command and overwrite the default value of the given
	 * command.
	 *
	 * Do not use this method after calling the analyze method, otherwise
	 * recursive command usage is not effected by the new default value!
	 *
	 * @param <V>
	 * @param <C>
	 * @param commandClass
	 * @param defaultValue
	 */
	public static synchronized <V, C extends AbstractRunCommand<V>> void registerCommand(Class<C> commandClass, V defaultValue) {
		if (argumentsAnalyzed) {
			LOGGER.warn("Command modification after argumend analysis detected! Read CLParser doc for more information.");

		}
		registeredCommandClasses.add(commandClass);
		overwrittenDefaultValueMap.put(commandClass, defaultValue);
	}

	/**
	 * Overwrites the default value of the given command without displaying the
	 * command in the help overview, For overwriting a regular command default
	 * value, use the command registration method instead.
	 *
	 * Do not use this method after calling the analyze method, otherwise
	 * recursive command usage is not effected by the new default value!
	 *
	 * @param <V>
	 * @param <C>
	 * @param commandClass
	 * @param defaultValue
	 */
	public static synchronized <V, C extends AbstractRunCommand<V>> void overwriteDefaultValue(Class<C> commandClass, V defaultValue) {
		if (argumentsAnalyzed) {
			LOGGER.warn("Command modification after argumend analysis detected! Read CLParser doc for more information.");
		}
		overwrittenDefaultValueMap.put(commandClass, defaultValue);
	}

	/**
	 * Register new command line command.
	 *
	 * @param commandClass
	 */
	public static void registerCommand(Class<? extends AbstractRunCommand> commandClass) {
		if (argumentsAnalyzed) {
			LOGGER.warn("Command modification after argumend analysis detected! Read CLParser doc for more information.");
		}
		registeredCommandClasses.add(commandClass);
	}

	/**
	 * Analyze the input arguments and setup all registered CLCommands. If one
	 * argument could not be handled or something else goes wrong this methods
	 * calls System.exit(255);
	 *
	 * Make sure all desired commands are registered before calling this method.
	 * Otherwise the commands will not be listed in the help screen.
	 *
	 * @param args
	 */
	public static void analyseAndExitOnError(String[] args) {
		try {
			CLParser.analyse(args);
		} catch (CLParserException ex) {
			CLParser.printHelp();
			printError(ex);
			LOGGER.info("Exit...");
			System.exit(255);
		}
	}

	private static void printError(Throwable cause) {
		LOGGER.error(cause.getMessage());
		Throwable innerCause = cause.getCause();
		if (innerCause != null) {
			printError(innerCause);
		}
	}

	private static void printValueModification(String[] args) {
		String argsString = "";
		for (String arg : args) {
			if (arg.startsWith("--")) {
				argsString += "\n\t";
			} else if (arg.startsWith("-")) {
				argsString += "\n\t ";
			} else {
				argsString += " ";
			}
			argsString += arg;
		}
		argsString += "\n";

		LOGGER.info("[command line value modification]" + argsString);
	}

	private static void initCommands() throws CLParserException {
		for (Class<? extends AbstractRunCommand> commandClass : registeredCommandClasses) {
			if (!runCommands.containsKey(commandClass)) {
				initCommand(commandClass);
			}
		}
	}

	private static void initCommand(Class<? extends AbstractRunCommand> commandClass) throws CLParserException {
		try {
			if (!registeredCommandClasses.contains(commandClass)) {
				registeredCommandClasses.add(commandClass);
			}
			AbstractRunCommand newInstance = commandClass.newInstance();
			if (overwrittenDefaultValueMap.containsKey(commandClass)) {
				newInstance.overwriteDefaultValue(overwrittenDefaultValueMap.get(commandClass));
			}
			runCommands.put(commandClass, newInstance);

		} catch (Exception ex) {
			throw new CommandInitializationException("Could not init " + commandClass.getSimpleName(), ex);
		}
	}

	/**
	 * Analyze the input arguments and setup all registered CLCommands.
	 *
	 * Make sure all desired commands are registered before calling this method.
	 * Otherwise the commands will not be listed in the help screen.
	 *
	 * @param args
	 * @throws CLParserException
	 */
	public static void analyse(String[] args) throws CLParserException {
		argumentsAnalyzed = true;
		try {
			printValueModification(args);
			initCommands();
			parseArguments(args);
		} catch (Exception ex) {
			throw new CLParserException("Coult not analyse arguments: " + ex.getMessage(), ex);
		}

	}

	private static void parseArguments(String[] args) throws CLParserException {

		AbstractRunCommand lastCommand = null;
		for (String arg : args) {
			arg = arg.trim();
			if (arg.equals("--")) { // handle final pattern
				break;
			}
			if (arg.startsWith("-") || arg.startsWith("--")) { // handle command
				boolean unknownCommand = true;
				for (AbstractRunCommand command : runCommands.values()) {

					if (command.match(arg)) {
						lastCommand = command;
						lastCommand.reset(); // In case of command overwriting during script recursion. Example: -p 5 -p 9 
						unknownCommand = false;
						break;
					}
				}
				if (unknownCommand) {
					throw new ParsingException("unknown parameter: " + arg);
				}
			} else {
				if (lastCommand == null) {
					throw new ParsingException("= bad parameter: " + arg);
				}
				lastCommand.addArgument(arg);
			}
		}

		for (AbstractRunCommand command : runCommands.values()) {
			if (command.isIdentifiered()) {
				try {
					command.parseArguments();
				} catch (Exception ex) {
					throw new BadArgumentException("Could not parse " + command + "!", ex);
				}
			}
		}

		for (AbstractRunCommand command : runCommands.values()) {
			command.updateValue();
		}

		for (AbstractRunCommand command : runCommands.values()) {
			try {
				command.validate();
			} catch (Exception ex) {
				throw new ValidationException("Could not validate " + command + "!", ex);
			}
		}
	}

	/**
	 * Returns the current value of the given command line class.
	 *
	 * If the command is never registered but the class is known in the
	 * classpath, the method returns the default value. In any case of error an
	 *
	 * @param <C>
	 * @param commandClass command class which defines the command.
	 * @return the current value of the given command type.
	 * @throws CLParserRuntimeException Method throws this Exception in any
	 * error case. For more details of the error access the
	 * innerCLParserException delivered by the CLParserRuntimeException.
	 */
	public static synchronized <C extends AbstractRunCommand> C getAttribute(Class<C> commandClass) throws CLParserRuntimeException {
		try {
			if (commandClass == null) {
				throw new NullPointerException("Nullpointer for commandClass given!");
			}

			if (!runCommands.containsKey(commandClass)) {
				initCommand(commandClass);
			}
			return (C) runCommands.get(commandClass);
		} catch (CLParserException ex) {
			throw new CLParserRuntimeException("Could not getAttribute for " + commandClass.getSimpleName() + "!", ex);
		}
	}

	/**
	 * Returns the configurated application name.
	 *
	 * @return the application name.
	 */
	public static String getProgramName() {
		return programName;
	}

	/**
	 * Method prints the help screen.
	 */
	public static void printHelp() {
		String help = "usage: " + programName;
		String header = "";
		List<AbstractRunCommand> commandList = new ArrayList(runCommands.values());
		Collections.sort(commandList);
		for (AbstractRunCommand command : commandList) {
			header += " [" + command.getSyntax() + "]";
		}
		help += newLineFormatter(header, "\n\t", 100);;
		help += "\nwhere:\n";
		for (AbstractRunCommand command : commandList) {
			help += "\t" + command.getSyntax() + " " + getDefault(command);
			help += "\n ";
			help += "\t\t" + newLineFormatter(command.getDescription(), "\n\t\t", 100);
			help += "\n";
		}

		LOGGER.info(help);
	}

	private static String getDefault(AbstractRunCommand command) {
		return "[Default: " + command.getExample() + "]";
	}

	public static String newLineFormatter(String text, String newLineOperator, int maxChars) { // TODO implement dyn prefix
		String[] textArray = text.split(" ");
		text = "";
		int charCounter = 0;

		for (int i = 0; i < textArray.length; i++) {
			if ((charCounter + textArray[i].length()) >= maxChars) {
				text += newLineOperator + textArray[i];
				charCounter = textArray[i].length();
			} else {
				text += textArray[i];

				if (textArray[i].contains("\n")) {
					charCounter = textArray[i].indexOf("\n");
				} else {
					charCounter += textArray[i].length();
				}
			}
			if (i != textArray.length - 1) {
				text += " ";
			}
		}
		return text;
	}

	public void saveProperties() {
		Properties properties = new Properties();

		for (AbstractRunCommand command : runCommands.values()) {

			/* check if property is modifiered */
			if (command.getCommandDefaultValue() != command.getValue()) {
				properties.put(command.getClass().getName(), command.getValue());
			}

			try {
				FileOutputStream fos = new FileOutputStream(CLParser.getAttribute(CLPropertyFile.class).getValue());
				properties.store(fos, "MyCommands");
			} catch (IOException ex) {
				LOGGER.error("Could not save properties!", ex);
			}

		}

	}
}
