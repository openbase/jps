/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2016 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import org.openbase.jps.core.AbstractJavaProperty;
import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPBadArgumentException;
import org.openbase.jps.exception.JPNotAvailableException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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
