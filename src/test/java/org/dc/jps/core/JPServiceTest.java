/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.core;

import org.dc.jps.core.JPService;
import org.dc.jps.core.helper.JPBaseDirectory;
import org.dc.jps.core.helper.JPChildDirectory;
import org.dc.jps.core.helper.JPDefaultValueRecursion;
import org.dc.jps.core.helper.JPMapStringString;
import org.dc.jps.preset.JPDebugMode;
import org.dc.jps.preset.JPShowGUI;
import org.dc.jps.preset.JPTestMode;
import org.dc.jps.preset.JPVerbose;
import java.io.File;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
 */
public class JPServiceTest {

    public JPServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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
     * @throws org.dc.jps.exception.JPServiceException
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
     * @throws org.dc.jps.exception.JPServiceException
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
     * @throws org.dc.jps.exception.JPServiceException
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
     * @throws org.dc.jps.exception.JPServiceException
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
     * @throws org.dc.jps.exception.JPServiceException
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
     * @throws org.dc.jps.exception.JPServiceException
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
        assertEquals(new File("/tmp/test/base"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/base/child"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_2() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/appbase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/appbase/appchild/sub"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_3() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--base", "/tmp/test/combase"};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/combase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/combase/appchild/sub"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_4() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--child", "comchild"};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/appbase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/appbase/comchild"), JPService.getProperty(JPChildDirectory.class).getValue());
    }

    @Test
    public void testPropertyDepHandling_5() throws Exception {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--base", "/tmp/test/combase", "--child", "comchild"};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/combase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/combase/comchild"), JPService.getProperty(JPChildDirectory.class).getValue());
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
        String[] args = {"--base", "/tmp/test/newbase"};
        JPService.parse(args);

        assertEquals("/tmp/test/newbase/child", JPService.getProperty(JPChildDirectory.class).getValue().getAbsolutePath());
    }

    @Test
    public void testDefaultValueRecursion() throws Exception {
        JPService.registerProperty(JPDefaultValueRecursion.class);
        JPService.registerProperty(JPChildDirectory.class);
        String[] args = {"--child", "child/revolution"};
        JPService.parse(args);
        assertEquals("/tmp/test/base/child/revolution", JPService.getProperty(JPDefaultValueRecursion.class).getValue().getAbsolutePath());
    }
}
