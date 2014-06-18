/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.clparser.exception.BadArgumentException;
import java.util.List;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author
 * mpohling
 */
public abstract class AbstractCLDouble extends AbstractRunCommand<Double> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public AbstractCLDouble(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected Double parse(List<String> arguments) throws BadArgumentException {
		return Double.parseDouble(getOneArgumentResult());
	}
}
