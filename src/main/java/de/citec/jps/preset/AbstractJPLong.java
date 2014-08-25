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
 * @author
 * mpohling
 */
public abstract class AbstractJPLong extends AbstractJavaProperty<Long> {

	public AbstractJPLong(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected Long parse(List<String> arguments) throws BadArgumentException {
		return Long.parseLong(getOneArgumentResult());
	}
}
