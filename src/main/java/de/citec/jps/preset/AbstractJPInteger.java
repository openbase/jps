/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.BadArgumentException;
import java.util.List;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPInteger extends AbstractJavaProperty<Integer> {

	public AbstractJPInteger(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected Integer parse(List<String> arguments) throws BadArgumentException {
		return Integer.parseInt(getOneArgumentResult());
	}
}
