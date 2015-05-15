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
public abstract class AbstractJPString extends AbstractJavaProperty<String> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"STRING"};
        
	public AbstractJPString(String[] commandIdentifier) {
        super(commandIdentifier);
    }
    
    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }
	
	@Override
	protected String parse(List<String> arguments) throws BadArgumentException {
		return getOneArgumentResult();
	}
}