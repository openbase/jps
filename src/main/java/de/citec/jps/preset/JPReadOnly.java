/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.core.JPService;

/**
 *
 * @author mpohling
 */
public class JPReadOnly extends AbstractJPBoolean {

    public final static String[] COMMAND_IDENTIFIERS = {"--read-only", "-r"};

    public JPReadOnly() {
        super(COMMAND_IDENTIFIERS);
    }

    @Override
    public void validate() throws Exception {
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