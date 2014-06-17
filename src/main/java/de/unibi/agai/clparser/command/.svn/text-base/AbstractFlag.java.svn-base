/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import org.apache.log4j.Logger;

/**
 *
 * @author divine
 */
public abstract class AbstractFlag extends AbstractRunCommand<Boolean>{

	private final Logger LOGGER = Logger.getLogger(getClass());
	
	public final static String[] ARGUMENT_IDENTIFIERS = {};
	public final static Boolean[] DEFAULT_VALUES = {};

	public AbstractFlag(String[] COMMAND_IDENTIFIERS) {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, DEFAULT_VALUES);
	}

	@Override
	protected Boolean parse(String arg) throws Exception {
		return true;
	}
}
