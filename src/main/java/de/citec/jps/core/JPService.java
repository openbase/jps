/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.core;

import de.citec.jps.preset.JPHelp;
import de.citec.jps.preset.JPPropertyFile;
import de.citec.jps.exception.BadArgumentException;
import de.citec.jps.exception.JPServiceException;
import de.citec.jps.exception.JPServiceRuntimeException;
import de.citec.jps.exception.PropertyInitializationException;
import de.citec.jps.exception.ParsingException;
import de.citec.jps.exception.ValidationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java Property Service, this is the central lib controller used to initialize
 * and manage all properties.
 *
 * @author Divine <DivineThreepwood@gmail.com>
 *
 *
 * JPS Library can be used for managing the properties of an application. 
 * The argument definition is realized by registering classes which
 * extends the AbstractJavaProperty class. Common argument types are supported by the
 * preset properties (e.g. Integer, Boolean, String types).
 *
 * The library supports the generation of a properties overview page.
 */
public class JPService {

    
	private static final Logger logger = LoggerFactory.getLogger(JPService.class);
	private static Set<Class<? extends AbstractJavaProperty>> registeredPropertyClasses = new HashSet<Class<? extends AbstractJavaProperty>>();
	private static HashMap<Class<? extends AbstractJavaProperty>, AbstractJavaProperty> runProperties = new HashMap<Class<? extends AbstractJavaProperty>, AbstractJavaProperty>();
	private static HashMap<Class<? extends AbstractJavaProperty>, Object> overwrittenDefaultValueMap = new HashMap<Class<? extends AbstractJavaProperty>, Object>();
	private static String applicationName = "";
	private static boolean argumentsAnalyzed = false;

	static {
		registerProperty(JPHelp.class);
	}

	/**
	 * Set the application name. The name is displayed in the help screen in the
	 * property overview page.
	 *
	 * @param name the application name
	 */
	public static void setApplicationName(String name) {
		applicationName = name;
	}

	/**
	 * Returns the configurated application name.
	 *
	 * @return the application name.
	 */
	public static String getApplicationName() {
		return applicationName;
	}
	
	/**
	 * Register the given property and overwrite the default value of the given
	 * one.
	 *
	 * Do not use this method after calling the analyze method, otherwise
	 * recursive property usage is not effected by the new default value!
	 *
	 * @param <V>
	 * @param <C>
	 * @param propertyClass
	 * @param defaultValue
	 */
	public static synchronized <V, C extends AbstractJavaProperty<V>> void registerProperty(Class<C> propertyClass, V defaultValue) {
		if (argumentsAnalyzed) {
			logger.warn("Property modification after argumend analysis detected! Read CLParser doc for more information.");

		}
		registeredPropertyClasses.add(propertyClass);
		overwrittenDefaultValueMap.put(propertyClass, defaultValue);
	}

	/**
	 * Overwrites the default value of the given property without displaying the
	 * property in the help overview, For overwriting a regular property default
	 * value, use the property registration method instead.
	 *
	 * Do not use this method after calling the analyze method, otherwise
	 * recursive property usage is not effected by the new default value!
	 *
	 * @param <V>
	 * @param <C>
	 * @param propertyClass
	 * @param defaultValue
	 */
	public static synchronized <V, C extends AbstractJavaProperty<V>> void overwriteDefaultValue(Class<C> propertyClass, V defaultValue) {
		if (argumentsAnalyzed) {
			logger.warn("Property modification after argumend analysis detected! Read CLParser doc for more information.");
		}
		overwrittenDefaultValueMap.put(propertyClass, defaultValue);
	}

	/**
	 * Register new property.
	 *
	 * @param propertyClass
	 */
	public static void registerProperty(Class<? extends AbstractJavaProperty> propertyClass) {
		if (argumentsAnalyzed) {
			logger.warn("Property modification after argumend analysis detected! Read CLParser doc for more information.");
		}
		registeredPropertyClasses.add(propertyClass);
	}

	/**
	 * Analyze the input arguments and setup all registered Properties. If one
	 * argument could not be handled or something else goes wrong this methods
	 * calls System.exit(255);
	 *
	 * Make sure all desired properties are registered before calling this method.
	 * Otherwise the properties will not be listed in the help screen.
	 *
	 * @param args
	 */
	public static void parseAndExitOnError(String[] args) {
		try {
			JPService.parse(args);
		} catch (JPServiceException ex) {
			JPService.printHelp();
			printError(ex);
			logger.info("Exit "+applicationName);
			System.exit(255);
		}
	}

	private static void printError(Throwable cause) {
		logger.error(cause.getMessage());
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

		logger.info("[command line value modification]" + argsString);
	}

	private static void initProperties() throws JPServiceException {
		for (Class<? extends AbstractJavaProperty> propertyClass : registeredPropertyClasses) {
			if (!runProperties.containsKey(propertyClass)) {
				initProperties(propertyClass);
			}
		}
	}

	private static void initProperties(Class<? extends AbstractJavaProperty> propertyClass) throws JPServiceException {
		try {
			if (!registeredPropertyClasses.contains(propertyClass)) {
				registeredPropertyClasses.add(propertyClass);
			}
			AbstractJavaProperty newInstance = propertyClass.newInstance();
			if (overwrittenDefaultValueMap.containsKey(propertyClass)) {
				newInstance.overwriteDefaultValue(overwrittenDefaultValueMap.get(propertyClass));
			}
			runProperties.put(propertyClass, newInstance);

		} catch (Exception ex) {
			throw new PropertyInitializationException("Could not init " + propertyClass.getSimpleName(), ex);
		}
	}

	/**
	 * Analyze the input arguments and setup all registered Properties.
	 *
	 * Make sure all desired properties are registered before calling this method.
	 * Otherwise the properties will not be listed in the help screen.
	 *
	 * @param args
	 * @throws JPServiceException
	 */
	public static void parse(String[] args) throws JPServiceException {
		argumentsAnalyzed = true;
		try {
			printValueModification(args);
			initProperties();
			parseArguments(args);
		} catch (Exception ex) {
			throw new JPServiceException("Coult not analyse arguments: " + ex.getMessage(), ex);
		}

	}

	private static void parseArguments(String[] args) throws JPServiceException {

		AbstractJavaProperty lastProperty = null;
		for (String arg : args) {
			arg = arg.trim();
			if (arg.equals("--")) { // handle final pattern
				break;
			}
			if (arg.startsWith("-") || arg.startsWith("--")) { // handle property
				boolean unknownProperty = true;
				for (AbstractJavaProperty property : runProperties.values()) {

					if (property.match(arg)) {
						lastProperty = property;
						lastProperty.reset(); // In case of property overwriting during script recursion. Example: -p 5 -p 9 
						unknownProperty = false;
						break;
					}
				}
				if (unknownProperty) {
					throw new ParsingException("unknown parameter: " + arg);
				}
			} else {
				if (lastProperty == null) {
					throw new ParsingException("= bad parameter: " + arg);
				}
				lastProperty.addArgument(arg);
			}
		}

		for (AbstractJavaProperty property : runProperties.values()) {
			if (property.isIdentifiered()) {
				try {
					property.parseArguments();
				} catch (Exception ex) {
					throw new BadArgumentException("Could not parse " + property + "!", ex);
				}
			}
		}

		for (AbstractJavaProperty property : runProperties.values()) {
			property.updateValue();
		}

		for (AbstractJavaProperty property : runProperties.values()) {
			try {
				property.validate();
			} catch (Exception ex) {
				throw new ValidationException("Could not validate " + property + "!", ex);
			}
		}
	}
    
    /**
	 * Returns the current value of the given property line class.
	 *
	 * If the property is never registered but the class is known in the
	 * classpath, the method returns the default value. 
	 *
	 * @param <C>
	 * @param propertyClass property class which defines the property.
	 * @return the current value of the given property type.
	 * @throws JPServiceRuntimeException Method throws this Exception in any
	 * error case. For more details of the error access the
	 * innerCLParserException delivered by the CLParserRuntimeException.
	 */
    public static synchronized <C extends AbstractJavaProperty> C getProperty(Class<C> propertyClass) throws JPServiceRuntimeException {
        try {
			if (propertyClass == null) {
				throw new NullPointerException("Nullpointer for propertyClass given!");
			}

			if (!runProperties.containsKey(propertyClass)) {
				initProperties(propertyClass);
			}
			return (C) runProperties.get(propertyClass);
		} catch (JPServiceException ex) {
			throw new JPServiceRuntimeException("Could not getAttribute for " + propertyClass.getSimpleName() + "!", ex);
		}
    }

	/**
	 * Returns the current value of the given property line class.
	 *
	 * If the property is never registered but the class is known in the
	 * classpath, the method returns the default value.
	 *
	 * @param <C>
	 * @param propertyClass property class which defines the property.
	 * @return the current value of the given property type.
	 * @throws JPServiceRuntimeException Method throws this Exception in any
	 * error case. For more details of the error access the
	 * innerCLParserException delivered by the CLParserRuntimeException.
     * @deprecated methode is deprecated and will be removed in next release. Please use getProperty instead!
	 */
    @Deprecated
	public static synchronized <C extends AbstractJavaProperty> C getAttribute(Class<C> propertyClass) throws JPServiceRuntimeException {
		return getProperty(propertyClass);
	}

	/**
	 * Method prints the help screen.
	 */
	public static void printHelp() {
		String help = "usage: " + applicationName;
		String header = "";
		List<AbstractJavaProperty> propertyList = new ArrayList(runProperties.values());
		Collections.sort(propertyList);
		for (AbstractJavaProperty property : propertyList) {
			header += " [" + property.getSyntax() + "]";
		}
		help += newLineFormatter(header, "\n\t", 100);;
		help += "\nwhere:\n";
		for (AbstractJavaProperty property : propertyList) {
			help += "\t" + property.getSyntax() + " " + getDefault(property);
			help += "\n ";
			help += "\t\t" + newLineFormatter(property.getDescription(), "\n\t\t", 100);
			help += "\n";
		}
		logger.info(help);
	}

	private static String getDefault(AbstractJavaProperty property) {
		return "[Default: " + property.getExample() + "]";
	}

	public static String newLineFormatter(String text, String newLineOperator, int maxChars) {
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

//	public void saveProperties() {
//		Properties properties = new Properties();
//
//		for (AbstractJavaProperty property : runProperties.values()) {
//
//			/* check if property is modifiered */
//			if (property.getPropertyDefaultValue() != property.getValue()) {
//				properties.put(property.getClass().getName(), property.getValue());
//			}
//
//			try {
//				FileOutputStream fos = new FileOutputStream(JPService.getAttribute(JPPropertyFile.class).getValue());
//				properties.store(fos, "MyProperties");
//			} catch (IOException ex) {
//				LOGGER.error("Could not save properties!", ex);
//			}
//		}
//	}
}
