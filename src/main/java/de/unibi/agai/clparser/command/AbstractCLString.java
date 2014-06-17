/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.clparser.exception.BadArgumentException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author mpohling
 */
public abstract class AbstractCLString extends AbstractRunCommand<String> {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public AbstractCLString(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected String parse(List<String> arguments) throws BadArgumentException {
		return getOneArgumentResult();
	}
}