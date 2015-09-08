/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.JPBadArgumentException;
import java.util.List;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPFloat extends AbstractJavaProperty<Float> {
    
    private final static String[] ARGUMENT_IDENTIFIERS = {"FLOAT"};
    
	public AbstractJPFloat(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }
	
	@Override
	protected Float parse(List<String> arguments) throws JPBadArgumentException {
		return Float.parseFloat(getOneArgumentResult());
	}
}
