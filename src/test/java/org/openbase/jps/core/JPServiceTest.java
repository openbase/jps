package org.openbase.jps.core;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2022 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.junit.jupiter.api.*;
import org.openbase.jps.core.AbstractJavaProperty.ValueType;
import org.openbase.jps.core.helper.*;
import org.openbase.jps.core.helper.JPEnumTestProperty.TestEnum;
import org.openbase.jps.exception.JPServiceException;
import org.openbase.jps.preset.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import org.openbase.jps.preset.JPLogLevel.LogLevel;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPServiceTest {

    public JPServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() throws JPServiceException {
        JPService.registerProperty(JPLogLevel.class, LogLevel.DEBUG);
        JPService.setupJUnitTestMode();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        JPService.setApplicationName("JPService UnitTest");
        JPService.registerProperty(JPTestMode.class, true);
    }

    @AfterEach
    public void tearDown() {
        JPService.reset();
    }

    /**
     * Test of registerCommand method, of class JPService.
     *
     * @throws Exception
     */
    @Test
    public void testDebugFlagNotSet() throws Exception {
        String[] args = {"-v"};
        JPService.parse(args);
        Assertions.assertEquals(true, JPService.getProperty(JPVerbose.class).getValue());
    }

    /**
     * Test of registerCommand method, of class JPService.
     *
     * @throws Exception
     */
    @Test
    public void testDebugFlagSet() throws Exception {
        JPService.registerProperty(JPDebugMode.class);
        String[] args = {"-d"};
        JPService.parse(args);
        assertEquals(true, JPService.getProperty(JPDebugMode.class).getValue());
    }

    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testParsingError() {
        try {
            String[] args = {"UNKNOWNcOMMAND"};
            JPService.parse(args);
            fail("No error caught!");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

    /**
     * Test of registerCommand method, of class JPService.
     *
     * @throws Exception
     */
    @Test
    public void testApplicationDefaultValue() throws Exception {
        JPService.registerProperty(JPDebugMode.class, false);
        JPService.registerProperty(JPShowGUI.class, true);
        String[] args = {};
        JPService.parse(args);
        assertEquals(false, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(true, JPService.getProperty(JPShowGUI.class).getValue());
    }

    /**
     * Test of registerCommand method, of class JPService.
     *
     * @throws Exception
     */
    @Test
    public void testApplicationDefaultValueOverwrite() throws Exception {
        JPService.registerProperty(JPDebugMode.class, false);
        JPService.registerProperty(JPShowGUI.class, true);
        String[] args = {JPDebugMode.COMMAND_IDENTIFIERS[0], JPShowGUI.COMMAND_IDENTIFIERS[0], "false"};
        JPService.parse(args);
        assertEquals(true, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(false, JPService.getProperty(JPShowGUI.class).getValue());

    }

    /**
     * Test of registerCommand method, of class JPService.
     *
     * @throws Exception
     */
    @Test
    public void testPropertyDefaultValueOverwriteViaApp() throws Exception {
        JPService.registerProperty(JPShowGUI.class, false);
        JPService.registerProperty(JPDebugMode.class, true);
        String[] args = {};
        JPService.parse(args);
        assertEquals(true, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(false, JPService.getProperty(JPShowGUI.class).getValue());
    }

    /**
     * Test of registerCommand method, of class JPService.
     *
     * @throws Exception
     */
    @Test
    public void testPropertyDefaultValueOverwriteViaCom() throws Exception {
        JPService.registerProperty(JPDebugMode.class, true);
        JPService.registerProperty(JPShowGUI.class, false);
        String[] args = {JPDebugMode.COMMAND_IDENTIFIERS[0], "false", JPShowGUI.COMMAND_IDENTIFIERS[0], "true"};
        JPService.parse(args);
        assertEquals(false, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(true, JPService.getProperty(JPShowGUI.class).getValue());

    }

    @Test
    public void testPropertyDepHandling_1() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class);
        JPService.registerProperty(JPChildDirectory.class);
        String[] args = {};
        JPService.parse(args);
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "base"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "base/child"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_2() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {};
        JPService.parse(args);
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase/appchild/sub"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_3() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--base", JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/combase"};
        JPService.parse(args);
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "combase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "combase/appchild/sub"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_4() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--child", "comchild"};
        JPService.parse(args);
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase/comchild"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_5() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--base", JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/combase", "--child", "comchild"};
        JPService.parse(args);
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "combase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File(JPService.getProperty(JPTmpDirectory.class).getValue(), "combase/comchild"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testJPMapStringStringParser() throws Exception {
        JPService.registerProperty(JPMapStringString.class);
        String[] args = {"--test-map", "Key1=Value1", "Key2=Value2", "Key3=Value3"};
        JPService.parse(args);
        Map<String, String> keyValueMap = JPService.getProperty(JPMapStringString.class).getValue();

        assertEquals(keyValueMap.get("Key1"), "Value1");
        assertEquals(keyValueMap.get("Key2"), "Value2");
        assertEquals(keyValueMap.get("Key3"), "Value3");
    }

    @Test
    public void testHelpPage() throws Exception {
        JPService.registerProperty(JPMapStringString.class);
        String[] args = {"-h"};
        JPService.parse(args);
        assertTrue(true);
    }

    @Test
    public void testRecursivePropertyResolving() throws Exception {
        JPService.registerProperty(JPChildDirectory.class);
        String[] args = {"--base", JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/newbase"};
        JPService.parse(args);
        assertEquals(JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/newbase/child", JPService.getProperty(JPChildDirectory.class).getValue().getAbsolutePath());
    }

    @Test
    public void testDefaultValueRecursion() throws Exception {
        JPService.registerProperty(JPDefaultValueRecursion.class);
        JPService.registerProperty(JPChildDirectory.class);
        String[] args = {"--child", "child/revolution"};
        JPService.parse(args);
        assertEquals(JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/base/child/revolution", JPService.getProperty(JPDefaultValueRecursion.class).getValue().getAbsolutePath());
    }

    @Test
    public void testAbsolutChildRef() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class);
        JPService.registerProperty(JPChildDirectory.class);
        String[] args = {"--child", JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/absolut/child"};
        JPService.parse(args);
        assertEquals(JPService.getProperty(JPTmpDirectory.class).getValue().getAbsolutePath() + "/absolut/child", JPService.getProperty(JPChildDirectory.class).getValue().getAbsolutePath());
    }

    @Test
    public void testPrinting() throws Exception {
        LoggerFactory.getLogger("TestMessage").debug("This is a debug message");
        LoggerFactory.getLogger("TestMessage").info("This is a info message");
        LoggerFactory.getLogger("TestMessage").warn("This is a important warning!");
        LoggerFactory.getLogger("TestMessage").error("This is an error message!");
    }

    @Test
    public void testJavaPropertyHandling() throws Exception {
        final String testEmptyProperty = "org.openbase.jps.testEmptyProperty";
        final String testProperty = "org.openbase.jps.testProperty";
        final String testPropertyValue = "handled";
        String[] args = {"-D" + testProperty + "=" + testPropertyValue, "-D" + testEmptyProperty};
        JPService.parse(args);
        assertEquals(testPropertyValue, System.getProperty(testProperty));
        assertEquals("", System.getProperty(testEmptyProperty));
    }

    @Test
    public void testJPLocale() throws Exception {
        JPService.registerProperty(JPLocale.class);
        String[] args = {"--locale", "DE"};
        JPService.parse(args);
        assertEquals(JPService.getProperty(JPLocale.class).getValue().getLanguage(), "de");
    }

    @Test
    public void testBadJPLocale() throws Exception {
        JPService.registerProperty(JPLocale.class);
        String[] args = {"--locale", "uhthe"};
        try {
            JPService.parse(args);
            fail("JPServiceException should be thrown!");
        } catch (JPServiceException ex) {
            // finish
            assertEquals("Given Language is unknown!", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    @Test
    public void testParseAndExitOnError() {
        String[] testArgs = {"-t"};
        JPService.registerProperty(JPTestProperty.class);
        try {
            JPService.parseAndExitOnError(testArgs);
            fail("Should have exited!");
        } catch (RuntimeException ex) {
            // finish
        }
    }

    @Test
    public void testNullPointerForUninitializedProperties() throws Exception {
        String[] testArgs = {"-t"};
        JPService.registerProperty(JPTestProperty.class);
        try {
            JPService.parse(testArgs);
            fail("Should have exited!");
        } catch (JPServiceException ex) {
            // finish
            assertEquals("Missing property arguments!", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    @Test
    public void testBooleanPropertyDefaultValue() throws Exception {
        String[] testArgs = {"--"};
        try {
            assertEquals(ValueType.PropertyDefault, JPService.getProperty(JPBooleanTestProperty.class).getValueType(), "Type need to be property default.");
            assertEquals(true, JPService.getProperty(JPBooleanTestProperty.class).getValue(), "Default value should have been true!");
        } catch (JPServiceException ex) {
            // finish
            fail("Should not fail!");
        }
    }

    @Test
    public void testSkipUnknownPropertiesDuringParsing() throws Exception {
        String[] testArgs = {"--xyz", "unknown", "-t", "custom"};
        JPService.registerProperty(JPTestProperty.class);
        try {
            JPService.parse(testArgs, true);
        } catch (JPServiceException ex) {
            ex.printStackTrace(System.err);
            fail("Should not fail!");
        }
    }

    @Test
    public void testGetPreEvaluatedValue() throws Exception {
        final String magicValue = "custom";
        String[] testArgs = {"--xyz", "unknown", "-t", magicValue};
        JPService.registerProperty(JPTestProperty.class);
        try {
            assertEquals(magicValue, JPService.getPreEvaluatedValue(JPTestProperty.class, testArgs), "Pre-evaluation returned wrong value!");
        } catch (JPServiceException ex) {
            fail("Should not fail!");
        }
    }

    @Test
    public void testGetPreEvaluatedValueWhenWrong() throws Exception {
        final String magicValue = "custom";
        String[] testArgs = {"--xyz", "unknown", "-t"};
        JPService.registerProperty(JPTestProperty.class);
        try {
            assertEquals(magicValue, JPService.getPreEvaluatedValue(JPTestProperty.class, testArgs), "Pre-evaluation returned wrong value!");
            fail("Did not fail after property is missing!");
        } catch (JPServiceException ex) {
            // should fail since property value is missing
            assertEquals("Missing property arguments!", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    @Test
    public void testGetPreEvaluatedValueAlternative() throws Exception {
        final TestEnum alternative = TestEnum.GAMMA;
        String[] testArgs = {"--enum", "INVALID"};
        JPService.registerProperty(JPEnumTestProperty.class);
        assertEquals(alternative, JPService.getPreEvaluatedValue(JPEnumTestProperty.class, testArgs, alternative),"Pre-evaluation returned wrong value!");
    }

    @Test
    public void testGetPreEvaluatedValueAfterParsing() throws Exception {
        String[] testArgs = {"--enum", "GAMMA"};
        JPService.registerProperty(JPEnumTestProperty.class);
        JPService.parse(testArgs);
        // after parsing an exception should be thrown.
        try {
            JPService.getPreEvaluatedValue(JPEnumTestProperty.class, testArgs,  TestEnum.BETA);
            fail("Did not fail while pre evaluation was requested after parsing!");
        } catch (RuntimeException ex) {
            // should be thrown
            assertEquals("Pre evaluated java property value was requested after the application arguments were parsed!", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    @Test
    public void testShortProperties() throws Exception {
        String[] testArgs = {"-bs"};
        JPService.registerProperty(JPBooleanTestProperty.class);
        JPService.registerProperty(JPBooleanSecondTestProperty.class);
        JPService.parse(testArgs);

        assertTrue(JPService.getValue(JPBooleanTestProperty.class), "Primary flag was not set!");
        assertTrue(JPService.getValue(JPBooleanSecondTestProperty.class), "Secondary flag was not set!");
    }

    @Test
    public void testParsingErrorHandlingInvalidPropertyName() throws Exception {
        String[] testArgs = {"-voeu", "oaeiuoeu"};
        //JPService.getPreEvaluatedValue(JPBooleanSecondTestProperty.class, testArgs);
        JPService.registerProperty(JPTestProperty.class);
        try {
            JPService.parse(testArgs);
            fail("Did not fail while parsing invalid command line arguments");
        } catch (JPServiceException ex) {
            // should be thrown
            assertEquals("unknown property: -o", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    private String getInitialExceptionMessage(final Throwable th) {
        if(th.getCause() != null && th.getCause().getMessage() != null) {
            return getInitialExceptionMessage(th.getCause());
        }
        return th.getMessage();
    }

    @Test
    public void testParsingErrorHandlingUnknownProperty() throws Exception {
        String[] testArgs = {"--invalid", "oaeiuoeu"};
        JPService.registerProperty(JPEnumTestProperty.class);
        try {
            JPService.parse(testArgs);
            fail("Did not fail while parsing invalid command line arguments");
        } catch (JPServiceException ex) {
            // should be thrown
            assertEquals("unknown property: --invalid", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    @Test
    public void testParsingErrorHandlingInvalidArgument() throws Exception {
        String[] testArgs = {"--enum", "oaeiuoeu"};
        JPService.registerProperty(JPEnumTestProperty.class);
        try {
            JPService.parse(testArgs);
            fail("Did not fail while parsing invalid command line arguments");
        } catch (JPServiceException ex) {
            // should be thrown
            assertEquals("No enum constant org.openbase.jps.core.helper.JPEnumTestProperty.TestEnum.OAEIUOEU", getInitialExceptionMessage(ex), "Wrong exception message!");
        }
    }

    @Test
    public void testHelpPrinting() throws Exception {
        String[] testArgs = {"--help"};
        JPService.getPreEvaluatedValue(JPBooleanSecondTestProperty.class, testArgs);
        JPService.registerProperty(JPBooleanSecondTestProperty.class);
        JPService.parseAndExitOnError(testArgs);
    }

    @Test
    public void testAppNameGeneration() {
        JPService.setApplicationName(JPService.class);
        JPService.setSubmoduleName(JPServiceTest.class);

        assertEquals("jpservice", JPService.getApplicationName(), "wrong application name generated");
        assertEquals("test", JPService.getSubmoduleName(), "wrong submodule name generated");

        JPService.setApplicationName("my-bad-controller");
        JPService.setSubmoduleName("sub-module-launcher");

        assertEquals("my-bad", JPService.getApplicationName(), "wrong application name generated");
        assertEquals("sub-module", JPService.getSubmoduleName(), "wrong submodule name generated");
    }
}
