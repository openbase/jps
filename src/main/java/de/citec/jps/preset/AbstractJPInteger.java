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
    
    public final static String[] ARGUMENT_IDENTIFIERS = {"INTEGER"};

    /**
     *
     * @param commandIdentifier
     * @param argumentIdentifiers
     * @deprecated overwrite generateArgumentIdentifiers(); for default argument identifier modification.
     */
    @Deprecated
	public AbstractJPInteger(String[] commandIdentifier, String[] argumentIdentifiers) {
		super(commandIdentifier, argumentIdentifiers);
	}
    
	public AbstractJPInteger(String[] commandIdentifier) {
        super(commandIdentifier);
    }
    
    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }
	
	@Override
	protected Integer parse(List<String> arguments) throws BadArgumentException {
		return Integer.parseInt(getOneArgumentResult());
	}
}
