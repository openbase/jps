/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPBadArgumentException;
import org.dc.jps.exception.JPNotAvailableException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPMethod<C> extends AbstractJavaProperty<Method> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"METHOD"};
    private final Class<? extends AbstractJPClass<C>> jpClass;

    public AbstractJPMethod(String[] commandIdentifier, Class<? extends AbstractJPClass<C>> jpClass) {
        super(commandIdentifier);
        this.jpClass = jpClass;
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Method parse(List<String> arguments) throws JPBadArgumentException {
        String oneArgumentResult = getOneArgumentResult();
        try {
            Class<C> relatedClass = JPService.getProperty(jpClass).getValue();
            for (Method method : relatedClass.getMethods()) {
                System.out.println("Method: "+method.getName());
                if (method.getName().equalsIgnoreCase(oneArgumentResult)) {
                    return method;
                }
            }
            throw new JPBadArgumentException("Class["+relatedClass.getClass().getName()+"] does not provide Method["+oneArgumentResult+"]!");
        } catch (JPNotAvailableException | SecurityException | JPBadArgumentException ex) {
            throw new JPBadArgumentException("Could not load given Method["+oneArgumentResult+"]!", ex);
        }
    }
}
