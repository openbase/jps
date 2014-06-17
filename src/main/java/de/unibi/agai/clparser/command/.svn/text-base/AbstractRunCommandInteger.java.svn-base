/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import org.apache.log4j.Logger;

/**
 *
 * @author
 * mpohling
 */
public abstract class AbstractRunCommandInteger extends AbstractRunCommand<Integer> {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public AbstractRunCommandInteger(String[] commandIdentifier, String[] argumentIdentifiers, Integer[] defaultValues) {
		super(commandIdentifier, argumentIdentifiers, defaultValues);
	}
	
	@Override
	protected Integer parse(String arg) throws Exception {
		return Integer.parseInt(arg);
	}
}
