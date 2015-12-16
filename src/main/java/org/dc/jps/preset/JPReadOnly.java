/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPValidationException;

/**
 *
 * @author mpohling
 */
public class JPReadOnly extends AbstractJPBoolean {

    public final static String[] COMMAND_IDENTIFIERS = {"--read-only"};

    public JPReadOnly() {
        super(COMMAND_IDENTIFIERS);
    }

    @Override
    public void validate() throws JPValidationException {
        super.validate();
        if (!getValueType().equals((AbstractJavaProperty.ValueType.PropertyDefault))) {
            logger.warn(JPService.getApplicationName() + " started in read only mode!");
        }
    }

    @Override
    public String getDescription() {
        return "Starts the "+JPService.getApplicationName()+" in a read only mode!";
    }
}