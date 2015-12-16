/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
 * @param <KEY>
 * @param <VALUE>
 */
public abstract class AbstractJPMap<KEY, VALUE>  extends AbstractJavaProperty<Map<KEY, VALUE>> {

    public final static String KEY_VALUE_SEPARATOR = "=";
    public final static String[] ARGUMENT_IDENTIFIERS = {"KEY=VALUE"};
        
	public AbstractJPMap(String[] commandIdentifier) {
        super(commandIdentifier);
    }
    
    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Map<KEY, VALUE> getPropertyDefaultValue() {
        return Collections.emptyMap();
    }
}