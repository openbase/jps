package org.openbase.jps.core;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2018 openbase.org
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

import org.junit.*;
import org.openbase.jps.core.AbstractJavaProperty.ValueType;
import org.openbase.jps.core.helper.*;
import org.openbase.jps.exception.JPServiceException;
import org.openbase.jps.preset.*;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPServiceTest {

    public JPServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws JPServiceException {
        JPService.setupJUnitTestMode();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        JPService.setApplicationName("JPService UnitTest");
        JPService.registerProperty(JPTestMode.class, true);
    }

    @After
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
        assertEquals(true, JPService.getProperty(JPVerbose.class).getValue());
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
            assertTrue("No error catched!", false);
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
            Assert.fail("JPNotAvailableException should not be thrown!");
        } catch (JPServiceException ex) {
            // finish
        }
    }

    @Test
    public void testParseAndExitOnError() {
        String[] testArgs = {"-t"};
        JPService.registerProperty(JPTestProperty.class);
        try {
            JPService.parseAndExitOnError(testArgs);
            Assert.fail("Should have exited!");
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
            Assert.fail("Should have exited!");
        } catch (JPServiceException ex) {
            // finish
        }
    }

    @Test
    public void testBooleanPropertyDefaultValue() throws Exception {
        String[] testArgs = {"--"};
//        JPService.registerProperty(JPBooleanTestProperty.class);
        try {
            Assert.assertEquals("Type need to be property default.", ValueType.PropertyDefault, JPService.getProperty(JPBooleanTestProperty.class).getValueType());
            Assert.assertEquals("Default value should have been true!", true, JPService.getProperty(JPBooleanTestProperty.class).getValue());
        } catch (JPServiceException ex) {
            // finish
        }
    }
}
