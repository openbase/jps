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
 * @author divine
 */
public abstract class AbstractJPBoolean extends AbstractJavaProperty<Boolean> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"BOOLEAN"};

    /**
     *
     * @param commandIdentifier
     * @param argumentIdentifiers
     * @deprecated overwrite generateArgumentIdentifiers(); for default argument identifier modification.
     */
    @Deprecated
    public AbstractJPBoolean(String[] commandIdentifier, String[] argumentIdentifiers) {
        super(commandIdentifier, argumentIdentifiers);
    }

    public AbstractJPBoolean(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Boolean parse(List<String> arguments) throws BadArgumentException {
        checkArgumentCount(0, 1);
        if (arguments.isEmpty()) { // parse as flag
            return true;
        }
        return Boolean.parseBoolean(arguments.get(0)); // parse as argument
    }

    @Override
    protected Boolean getPropertyDefaultValue() {
        return false;
    }
}
