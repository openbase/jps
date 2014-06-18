/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.BadArgumentException;
import java.util.List;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author
 * mpohling
 */
public abstract class AbstractJPDouble extends AbstractJavaProperty<Double> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public AbstractJPDouble(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected Double parse(List<String> arguments) throws BadArgumentException {
		return Double.parseDouble(getOneArgumentResult());
	}
}
