/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.BadArgumentException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author mpohling
 * @param <C> property related enum type.
 */
public abstract class AbstractJPClass<C> extends AbstractJavaProperty<Class<C>> {

    public AbstractJPClass(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        String[] args = new String[1];
        args[0] = detectTypeClass().getSimpleName().toUpperCase();
        return args;
    }

    @Override
    protected Class<C> parse(List<String> arguments) throws BadArgumentException {
        String oneArgumentResult = getOneArgumentResult();
        Class clazz;
        try {
             clazz = getClass().getClassLoader().loadClass(oneArgumentResult);
        } catch (ClassNotFoundException ex) {
            throw new BadArgumentException("Could not load given Class!", ex);
        }
        try {
             return (Class<C>) clazz;
        } catch (ClassCastException ex) {
            throw new BadArgumentException("Given Class["+clazz.getName()+"] is not a instance of Class["+detectTypeClass().getName()+"]!", ex);
        }
    }

    public Class<C> detectTypeClass() {
        return (Class<C>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
