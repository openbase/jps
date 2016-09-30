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
import org.openbase.jps.exception.JPBadArgumentException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
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
    protected Class<C> parse(List<String> arguments) throws JPBadArgumentException {
        String oneArgumentResult = getOneArgumentResult();
        Class clazz;
        try {
             clazz = getClass().getClassLoader().loadClass(oneArgumentResult);
        } catch (ClassNotFoundException ex) {
            throw new JPBadArgumentException("Could not load given Class!", ex);
        }
        try {
             return (Class<C>) clazz;
        } catch (ClassCastException ex) {
            throw new JPBadArgumentException("Given Class["+clazz.getName()+"] is not a instance of Class["+detectTypeClass().getName()+"]!", ex);
        }
    }

    public Class<C> detectTypeClass() {
        return (Class<C>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
