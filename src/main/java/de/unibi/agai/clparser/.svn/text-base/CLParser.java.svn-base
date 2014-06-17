/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser;

import de.unibi.agai.clparser.command.PrintHelpCommand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 * 
 * TODOList:
 * TODO make command default values setable
 * TODO resolve command loading order to provide recusiv command value usage.
 */
public class CLParser {

	private static final Logger LOGGER = Logger.getLogger(CLParser.class);
	private static HashMap<Class<? extends AbstractRunCommand>, AbstractRunCommand> runCommands = new HashMap<Class<? extends AbstractRunCommand>, AbstractRunCommand>();
	private static String programName = "";

	public static void setProgramName(String name) {
		programName = name;
		addCommand(new PrintHelpCommand());

	}

	//FIXME: Calls recusive getAttribute without check!!
	public static void addCommand(AbstractRunCommand command) {
		runCommands.put(command.getClass(), command);
	}

	public static void analyseAndExitOnError(String[] args) {
		if (!CLParser.analyse(args)) {
			CLParser.printHelp();
			LOGGER.info("Exit...");
			System.exit(255);
		}
	}

	public static boolean analyse(String[] args) {

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
		argsString += "\n";

		AbstractRunCommand lastCommand = null;
		for (String arg : args) {
			arg = arg.trim();
			if (arg.equals("--")) {
				break;
			}
			if (arg.startsWith("-") || arg.startsWith("--")) {
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

					LOGGER.error("unknown parameter: " + arg);
					return false;
				}
			} else {
				if (lastCommand == null) {
					LOGGER.error("= bad parameter: " + arg);
					return false;
				}
				lastCommand.addArgument(arg);
			}
		}

		for (AbstractRunCommand command : runCommands.values()) {
			if (command.isIdentifiered()) {
				if (!command.parseArguments()) {
					return false;
				}
			}
			try {
				command.validate();
			} catch (Exception ex) {
				LOGGER.error("Could not validate " + command + "! " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
				return false;
			}
		}
		return true;
	}

	public static synchronized <C extends AbstractRunCommand> C getAttribute(Class<C> commandClass) {
		if (commandClass == null) {
			throw new NullPointerException("Could not getAttribute for Nullpointer!");
		}
		if (!runCommands.containsKey(commandClass)) {
			throw new RuntimeException("Could not getAttribute for " + commandClass.getSimpleName() + ". Command not registrated!");
		}
		return (C) runCommands.get(commandClass);
	}

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
		if (command.getArgumentIdentifiers().length == 0) {
			return "[Default: disabled]";
		}
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
//	private static String getDefault(AbstractRunCommand command) {
//		String buffer = "[default: ";
//			if(command.getArgumentIdentifiers().length == 0) {
//				buffer += "disabled";
//			} else {
//				for(int i=0; i<command.getArgumentIdentifiers().length;i++) {
//					buffer +=command.getArgumentIdentifiers()[i] + "=" + command.getDefaultValues()[i];
//					if(i<command.getArgumentIdentifiers().length-1) {
//						buffer += " ";
//					}
//				}
//			}
//			buffer += "]";
//		return buffer;
//	}
}
