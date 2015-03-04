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
 * @param <E> property related enum type.
 */
public abstract class AbstractJPEnum<E extends Enum<E>> extends AbstractJavaProperty<E> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"ENUM"};

    public AbstractJPEnum(String[] commandIdentifier) {
        super(commandIdentifier, ARGUMENT_IDENTIFIERS);
    }
    
    public AbstractJPEnum(String[] commandIdentifier, String[] argumentIdentifiers) {
        super(commandIdentifier, argumentIdentifiers);
    }

    @Override
    protected E parse(List<String> arguments) throws BadArgumentException {
        try {
            return Enum.valueOf(getDefaultValue().getDeclaringClass(), getOneArgumentResult());
        } catch (IllegalArgumentException ex) {
            return Enum.valueOf(getDefaultValue().getDeclaringClass(), getOneArgumentResult().toUpperCase());
        }
    }
}
