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
public abstract class AbstractJPFloat extends AbstractJavaProperty<Float> {
    
    private final static String[] ARGUMENT_IDENTIFIERS = {"FLOAT"};

	public AbstractJPFloat(String[] commandIdentifier) {
        this(commandIdentifier, ARGUMENT_IDENTIFIERS);
    }
    
	public AbstractJPFloat(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
	
	@Override
	protected Float parse(List<String> arguments) throws BadArgumentException {
		return Float.parseFloat(getOneArgumentResult());
	}
}
