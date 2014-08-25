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
 * @param <E> property related enum type.
 */
public abstract class AbstractJPEnum<E extends Enum<E>> extends AbstractJavaProperty<E> {

	public AbstractJPEnum(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected E parse(List<String> arguments) throws BadArgumentException {
		return Enum.valueOf(getDefaultValue().getDeclaringClass(), getOneArgumentResult());
	}
}
