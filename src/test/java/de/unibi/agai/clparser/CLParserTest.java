/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser;

import de.unibi.agai.clparser.command.DebugModeFlag;
import de.unibi.agai.clparser.command.ShowGUIFlag;
import de.unibi.agai.clparser.exception.CLParserException;
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
public class CLParserTest {
	
	public CLParserTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		CLParser.setProgramName("CLParser UnitTest");
	}
	
	@After
	public void tearDown() {
	}


	/**
	 * Test of registerCommand method, of class CLParser.
	 */
	@Test
	public void testDebugFlag() throws CLParserException {
		System.out.println("registerCommand");
		CLParser.registerCommand(DebugModeFlag.class);
		String[] args = {"-d"};
		CLParser.analyse(args);
		assertEquals(true, CLParser.getAttribute(DebugModeFlag.class).getValue());
	}
	
	/**
	 * Test of registerCommand method, of class CLParser.
	 */
	@Test
	public void testParsingError() {
		System.out.println("registerCommand");
		String[] args = {"UNKNOWNcOMMAND"};
		try {
			CLParser.analyse(args);
			assertTrue("No error catched!", false);
		} catch (CLParserException ex) {
		}
	}
	
	/**
	 * Test of registerCommand method, of class CLParser.
	 */
	@Test
	public void testApplicationDefaultValue() throws CLParserException {
		System.out.println("registerCommand");
		CLParser.registerCommand(DebugModeFlag.class);
		CLParser.registerCommand(ShowGUIFlag.class, true);
		String[] args = {"-d", "false"};
		CLParser.analyse(args);
		assertEquals(false, CLParser.getAttribute(DebugModeFlag.class).getValue());
		assertEquals(true, CLParser.getAttribute(ShowGUIFlag.class).getValue());
	}
}