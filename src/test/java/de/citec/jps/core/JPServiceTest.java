/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.core;

import de.citec.jps.preset.JPDebugMode;
import de.citec.jps.preset.JPShowGUI;
import de.citec.jps.exception.JPServiceException;
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
    }

    /**
     * Test of registerCommand method, of class JPService.
     */
    @Test
    public void testDebugFlag() throws JPServiceException {
        System.out.println("registerCommand");
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
        System.out.println("registerCommand");
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
        System.out.println("registerCommand");
        JPService.registerProperty(JPDebugMode.class);
        JPService.registerProperty(JPShowGUI.class, true);
        String[] args = {"-d", "false"};
        JPService.parse(args);
        assertEquals(false, JPService.getProperty(JPDebugMode.class).getValue());
        assertEquals(true, JPService.getProperty(JPShowGUI.class).getValue());
    }
}
