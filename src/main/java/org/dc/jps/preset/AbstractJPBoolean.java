/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2016 DivineCooperation
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
