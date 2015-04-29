/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.core;

import de.citec.jps.preset.JPHelp;
import de.citec.jps.exception.BadArgumentException;
import de.citec.jps.exception.JPServiceException;
import de.citec.jps.exception.JPServiceRuntimeException;
import de.citec.jps.exception.PropertyInitializationException;
import de.citec.jps.exception.ParsingException;
import de.citec.jps.exception.ValidationException;
import de.citec.jps.preset.JPTestMode;
import de.citec.jps.preset.JPVerbose;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java Property Service, this is the central lib controller used to initialize
 * and manage all properties.
 *
 * @author Divine Threepwood
 *
 *
 * JPS Library can be used for managing the properties of an application. The
 * argument definition is realized by registering classes which extends the
 * AbstractJavaProperty class. Common argument types are supported by the preset
 * properties (e.g. Integer, Boolean, String types).
 *
 * The library supports the generation of a properties overview page.
 */
public class JPService {

    private static final Logger logger = LoggerFactory.getLogger(JPService.class);
    private static final Set<Class<? extends AbstractJavaProperty>> registeredPropertyClasses = new HashSet<>();
    private static final HashMap<Class<? extends AbstractJavaProperty>, AbstractJavaProperty> initializedProperties = new HashMap<>();
    private static final HashMap<Class<? extends AbstractJavaProperty>, AbstractJavaProperty> loadedProperties = new HashMap<>();
    private static final HashMap<Class<? extends AbstractJavaProperty>, Object> overwrittenDefaultValueMap = new HashMap<>();
    private static String applicationName = "";
    private static boolean argumentsAnalyzed = false;

    static {
        registerProperty(JPHelp.class);
        registerProperty(JPVerbose.class);
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
            logger.warn("Property modification after argumend analysis detected! Read JPService doc for more information.");

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
            logger.warn("Property modification after argumend analysis detected! Read JPService doc for more information.");
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
            logger.warn("Property modification after argumend analysis detected! Read JPService doc for more information.");
        }
        registeredPropertyClasses.add(propertyClass);
    }

    /**
     * Analyze the input arguments and setup all registered Properties. If one
     * argument could not be handled or something else goes wrong this methods
     * calls System.exit(255);
     *
     * Make sure all desired properties are registered before calling this
     * method. Otherwise the properties will not be listed in the help screen.
     *
     * @param args
     */
    public static void parseAndExitOnError(String[] args) {
        try {
            JPService.parse(args);
        } catch (JPServiceException ex) {
            JPService.printHelp();
            printError(ex);
            logger.info("Exit " + applicationName);

            if (getProperty(JPVerbose.class).getValue()) {
                ex.printStackTrace(System.err);
            }
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

        if (args == null) {
            return;
        }

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

    private static void initRegisteredProperties() throws JPServiceException {
        initRegisteredProperties(null);
    }

    private static void initRegisteredProperties(final String[] args) throws JPServiceException {

        // reset already loaded properties.
        loadedProperties.clear();

        try {
            Collection<Class<? extends AbstractJavaProperty>> currentlyregisteredPropertyClasses = new HashSet(registeredPropertyClasses);
            for (Class<? extends AbstractJavaProperty> propertyClass : currentlyregisteredPropertyClasses) {
                if (!initializedProperties.containsKey(propertyClass)) {
                    initProperty(propertyClass);
                }
            }

            if (args != null) {
                parseArguments(args);
            }
        } catch (Exception ex) {
            throw new JPServiceException("Could not init registered properties!", ex);
        }
    }

    private static synchronized AbstractJavaProperty initProperty(Class<? extends AbstractJavaProperty> propertyClass) throws JPServiceException {

        try {
            // Avoid double initialization
            if (initializedProperties.containsKey(propertyClass)) {
                throw new JPServiceException("Already initialized!");
            }

            if (!registeredPropertyClasses.contains(propertyClass)) {
                registeredPropertyClasses.add(propertyClass);
            }
            AbstractJavaProperty newInstance = propertyClass.newInstance();

            initializedProperties.put(propertyClass, newInstance);
            return newInstance;
        } catch (Exception ex) {
            throw new PropertyInitializationException("Could not init " + propertyClass.getSimpleName(), ex);
        }
    }

    /**
     * Analyze the input arguments and setup all registered Properties.
     *
     * Make sure all desired properties are registered before calling this
     * method. Otherwise the properties will not be listed in the help screen.
     *
     * @param args
     * @throws JPServiceException
     */
    public static void parse(String[] args) throws JPServiceException {
        argumentsAnalyzed = true;
        try {
            printValueModification(args);
            initRegisteredProperties(args);
        } catch (Exception ex) {
            throw new JPServiceException("Could not analyse arguments: " + ex.getMessage(), ex);
        }
    }

    /**
     * Setup JPService for JUnitTests By using the JPService during JUnit Tests
     * it's recommended to call this method after property registration instead
     * using the parsing methods because command line property handling makes no
     * sense in the context of unit tests..
     *
     * The following properties are activated by default while running JPService
     * in TestMode:
     *
     * - JPVerbose is set to true to print more debug messages.
     *
     * - JPTestMode is activated.
     *
     * @throws JPServiceException
     */
    public static void setupJUnitTestMode() throws JPServiceException {
        try {
            registerProperty(JPVerbose.class, true);
            registerProperty(JPTestMode.class, true);
            initRegisteredProperties();
        } catch (ValidationException ex) {
            throw new JPServiceException("Could not setup JPService for UnitTestMode!", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadProperty(final AbstractJavaProperty property) throws JPServiceException {

        try {
            if (loadedProperties.containsKey(property.getClass())) {
                throw new JPServiceException("Already loaded!");
            }

            parseProperty(property);
            if (overwrittenDefaultValueMap.containsKey(property.getClass())) {
                property.overwriteDefaultValue(overwrittenDefaultValueMap.get(property.getClass()));
            }
            property.updateValue();
            property.validate();
        } catch (BadArgumentException | ValidationException ex) {
            throw new JPServiceException("Could not load " + property + "!", ex);
        }

        loadedProperties.put(property.getClass(), property);
    }

    private static void parseArguments(String[] args) throws JPServiceException {
        AbstractJavaProperty lastProperty = null;

        for (String arg : args) {
            try {
                arg = arg.trim();
                if (arg.equals("--")) { // handle final pattern
                    break;
                }
                if (arg.startsWith("-") || arg.startsWith("--")) { // handle property
                    boolean unknownProperty = true;
                    for (AbstractJavaProperty property : initializedProperties.values()) {

                        if (property.match(arg)) {
                            lastProperty = property;
                            lastProperty.reset(); // In case of property overwriting during script recursion. Example: -p 5 -p 9
                            unknownProperty = false;
                            break;
                        }
                    }
                    if (unknownProperty) {
                        throw new ParsingException("unknown property: " + arg);
                    }
                } else {
                    if (lastProperty == null) {
                        throw new ParsingException("= bad property: " + arg);
                    }
                    lastProperty.addArgument(arg);
                }
            } catch (JPServiceException ex) {
                throw new JPServiceException("Could not parse Argument[" + arg + "]!", ex);
            }
        }
    }

    private static void parseProperty(final AbstractJavaProperty property) throws BadArgumentException {
        if (property.isIdentifiered()) {
            try {
                property.parseArguments();
            } catch (Exception ex) {
                throw new BadArgumentException("Could not parse " + property + "!", ex);
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
                throw new NullPointerException("Given propertyClass is a Nullpointer!");
            }

            // load if not already done.
            if (!loadedProperties.containsKey(propertyClass)) {
                // init if not already done.
                if (!initializedProperties.containsKey(propertyClass)) {
                    initProperty(propertyClass);
                }
                loadProperty(initializedProperties.get(propertyClass));
            }
            return (C) loadedProperties.get(propertyClass);
        } catch (JPServiceException ex) {
            throw new JPServiceRuntimeException("Could not return property for " + propertyClass.getSimpleName() + "!", ex);
        }
    }

    /**
     * Method prints the help screen.
     */
    public static void printHelp() {
        String help = "usage: " + applicationName;
        String header = "";
        List<AbstractJavaProperty> propertyList = new ArrayList(initializedProperties.values());
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
