/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.agai.clparser.command;

import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public class DebugModeFlag extends AbstractFlag {
	

	private final Logger LOGGER = Logger.getLogger(getClass());
	
	public final static String[] COMMAND_IDENTIFIERS = {"-d", "--DebugMode"};

	public DebugModeFlag() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	public String getDescription() {
		return "Enables the debug mode.";
	}
}
