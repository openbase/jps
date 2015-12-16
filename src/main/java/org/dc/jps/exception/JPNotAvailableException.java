/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.exception;

import org.dc.jps.core.AbstractJavaProperty;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
 */
public class JPNotAvailableException extends JPServiceException {

    public JPNotAvailableException(Class<? extends AbstractJavaProperty> propertyClass, final Throwable cause) {
        super("Property["+propertyClass.getSimpleName()+"] is not available!", cause);
    }

}
