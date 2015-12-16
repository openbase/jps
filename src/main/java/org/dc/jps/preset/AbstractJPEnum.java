/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.exception.JPServiceException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author mpohling
 * @param <E> property related enum type.
 */
public abstract class AbstractJPEnum<E extends Enum<E>> extends AbstractJavaProperty<E> {

    public AbstractJPEnum(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        String[] id = {detectTypeClass().getSimpleName().toUpperCase()};
        return id;
    }

    @Override
    protected E parse(List<String> arguments) throws JPServiceException {
        try {
            return Enum.valueOf(getDefaultValue().getDeclaringClass(), getOneArgumentResult());
        } catch (IllegalArgumentException ex) {
            return Enum.valueOf(getDefaultValue().getDeclaringClass(), getOneArgumentResult().toUpperCase());
        }
    }

    public Class<E> detectTypeClass() {
        return (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
