/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.core;

import de.citec.jps.core.helper.JPBaseDirectory;
import de.citec.jps.core.helper.JPChildDirectory;
import de.citec.jps.core.helper.JPMapStringString;
import de.citec.jps.preset.JPDebugMode;
import de.citec.jps.preset.JPShowGUI;
import de.citec.jps.exception.JPServiceException;
import de.citec.jps.preset.JPVerbose;
import java.io.File;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mpohling
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
    }

    @After
    public void tearDown() {
        JPService.reset();
    }
    
    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testDebugFlagNotSet() throws JPServiceException {
        String[] args = {"-v"};
        JPService.parse(args);
        assertEquals(true, JPService.getProperty(JPVerbose.class).getValue());
    }

    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test 
   public void testDebugFlagSet() throws JPServiceException {
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
        String[] args = {"UNKNOWNcOMMAND"};
        try {
            JPService.parse(args);
            assertTrue("No error catched!", false);
        } catch (JPServiceException ex) {
        }
    }

    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testApplicationDefaultValue() throws JPServiceException {
        JPService.registerProperty(JPDebugMode.class, false);
        JPService.registerProperty(JPShowGUI.class, true);
        String[] args = {};
        JPService.parse(args);
        assertEquals(false, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(true, JPService.getProperty(JPShowGUI.class).getValue());
    }
    
    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testApplicationDefaultValueOverwrite() throws JPServiceException {
        JPService.registerProperty(JPDebugMode.class, false);
        JPService.registerProperty(JPShowGUI.class, true);
        String[] args = {JPDebugMode.COMMAND_IDENTIFIERS[0], JPShowGUI.COMMAND_IDENTIFIERS[0], "false"};
        JPService.parse(args);
        assertEquals(true, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(false, JPService.getProperty(JPShowGUI.class).getValue());
    }
    
    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testPropertyDefaultValueOverwriteViaApp() throws JPServiceException {
        JPService.registerProperty(JPShowGUI.class, false);
        JPService.registerProperty(JPDebugMode.class, true);
        String[] args = {};
        JPService.parse(args);
        assertEquals(true, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(false, JPService.getProperty(JPShowGUI.class).getValue());
    }
    
    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testPropertyDefaultValueOverwriteViaCom() throws JPServiceException {
        JPService.registerProperty(JPDebugMode.class, true);
        JPService.registerProperty(JPShowGUI.class, false);
        String[] args = {JPDebugMode.COMMAND_IDENTIFIERS[0], "false", JPShowGUI.COMMAND_IDENTIFIERS[0], "true"};
        JPService.parse(args);
        assertEquals(false, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(true, JPService.getProperty(JPShowGUI.class).getValue());
    
    }
    
    @Test
    public void testPropertyDepHandling_1() throws JPServiceException {
        JPService.registerProperty(JPBaseDirectory.class);
        JPService.registerProperty(JPChildDirectory.class);
        String[] args = {};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/base"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/base/child"), JPService.getProperty(JPChildDirectory.class).getValue());
    }
    
    @Test
    public void testPropertyDepHandling_2() throws JPServiceException {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/appbase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/appbase/appchild/sub"), JPService.getProperty(JPChildDirectory.class).getValue());
    }
    
    @Test
    public void testPropertyDepHandling_3() throws JPServiceException {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--base", "/tmp/test/combase"};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/combase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/combase/appchild/sub"), JPService.getProperty(JPChildDirectory.class).getValue());
    }
    
    @Test
    public void testPropertyDepHandling_4() throws JPServiceException {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--child", "comchild"};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/appbase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/appbase/comchild"), JPService.getProperty(JPChildDirectory.class).getValue());
    }
    
    @Test
    public void testPropertyDepHandling_5() throws JPServiceException {
        JPService.registerProperty(JPBaseDirectory.class, new File("/tmp/test/appbase/"));
        JPService.registerProperty(JPChildDirectory.class, new File("appchild/sub"));
        String[] args = {"--base", "/tmp/test/combase", "--child", "comchild"};
        JPService.parse(args);
        assertEquals(new File("/tmp/test/combase"), JPService.getProperty(JPBaseDirectory.class).getValue());
        assertEquals(new File("/tmp/test/combase/comchild"), JPService.getProperty(JPChildDirectory.class).getValue());
    }
    
    @Test
    public void testJPMapStringStringParser() throws JPServiceException {
        JPService.registerProperty(JPMapStringString.class);
        String[] args = {"--test-map", "Key1=Value1", "Key2=Value2", "Key3=Value3"};
        JPService.parse(args);
        Map<String, String> keyValueMap = JPService.getProperty(JPMapStringString.class).getValue();

        assertEquals(keyValueMap.get("Key1"), "Value1");
        assertEquals(keyValueMap.get("Key2"), "Value2");
        assertEquals(keyValueMap.get("Key3"), "Value3");
    }
    
    @Test
    public void testHelpPage() throws JPServiceException {
        JPService.registerProperty(JPMapStringString.class);
        String[] args = {"-h"};
        JPService.parse(args);
    }
}
