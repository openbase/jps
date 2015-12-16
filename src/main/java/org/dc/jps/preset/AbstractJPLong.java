/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.exception.JPBadArgumentException;
import java.util.List;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPLong extends AbstractJavaProperty<Long> {
    
    public final static String[] ARGUMENT_IDENTIFIERS = {"LONG"};
    
	public AbstractJPLong(String[] commandIdentifier) {
        super(commandIdentifier);
    }
    
    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }
	
	@Override
	protected Long parse(List<String> arguments) throws JPBadArgumentException {
		return Long.parseLong(getOneArgumentResult());
	}
}
