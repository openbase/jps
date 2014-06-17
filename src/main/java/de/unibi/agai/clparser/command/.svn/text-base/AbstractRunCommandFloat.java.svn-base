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
public abstract class AbstractRunCommandFloat extends AbstractRunCommand<Float> {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public AbstractRunCommandFloat(String[] commandIdentifier, String[] argumentIdentifiers, Float[] defaultValues) {
		super(commandIdentifier, argumentIdentifiers, defaultValues);
	}
	
	@Override
	protected Float parse(String arg) throws Exception {
		return Float.parseFloat(arg);
	}
}
