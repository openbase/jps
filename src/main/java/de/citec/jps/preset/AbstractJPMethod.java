/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.core.JPService;
import de.citec.jps.exception.BadArgumentException;
import de.citec.jps.exception.JPServiceRuntimeException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author mpohling
 */
public abstract class AbstractJPMethod<C> extends AbstractJavaProperty<Method> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"METHODE"};
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
    protected Method parse(List<String> arguments) throws BadArgumentException {
        String oneArgumentResult = getOneArgumentResult();
        try {
            Class<C> relatedClass = JPService.getProperty(jpClass).getValue();
            for (Method method : relatedClass.getMethods()) {
                System.out.println("Method: "+method.getName());
                if (method.getName().equalsIgnoreCase(oneArgumentResult)) {
                    return method;
                }
            }
            throw new BadArgumentException("Class["+relatedClass.getClass().getName()+"] does not provide Method["+oneArgumentResult+"]!");
        } catch (JPServiceRuntimeException | SecurityException | BadArgumentException ex) {
            throw new BadArgumentException("Could not load given Method["+oneArgumentResult+"]!", ex);
        }
    }
}
