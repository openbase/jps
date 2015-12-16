/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.exception.JPBadArgumentException;
import org.dc.jps.exception.JPNotAvailableException;
import java.util.List;

/**
 *
 * @author divine
 */
public abstract class AbstractJPBoolean extends AbstractJavaProperty<Boolean> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"BOOLEAN"};

    /**
     *
     * @param commandIdentifier
     */
    public AbstractJPBoolean(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Boolean parse(List<String> arguments) throws JPBadArgumentException {
        checkArgumentCount(0, 1);
        if (arguments.isEmpty()) { // parse as flag
            return true;
        }
        return Boolean.parseBoolean(arguments.get(0)); // parse as argument
    }

    @Override
    protected Boolean getPropertyDefaultValue() throws JPNotAvailableException {
        return false;
    }
}