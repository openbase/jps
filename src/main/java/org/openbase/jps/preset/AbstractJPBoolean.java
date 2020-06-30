/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2020 openbase.org
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
import org.openbase.jps.exception.JPNotAvailableException;

import java.util.List;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractJPBoolean extends AbstractJavaProperty<Boolean> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"BOOLEAN"};

    /**
     * @param commandIdentifier
     */
    public AbstractJPBoolean(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    /**
     * This is a advanced resolution implementation of multi properties
     *
     * @param commandIdentifier the possible command identifier.
     *
     * @return true if the property could be matched via an multi property declaration.
     */
    protected final boolean customMatch(final String commandIdentifier) {
        // check if command identifier is a combination out of different property identifies passed as single chars
        if (commandIdentifier.startsWith("-", 0) && !commandIdentifier.startsWith("-", 1) && commandIdentifier.length() > 2) {
            // skip initial "-" and therefor start by index 1 till end
            for (int i = 1; i < commandIdentifier.length(); i++) {
                // check if single char is know as property
                if (super.match("-" + commandIdentifier.substring(i, i + 1))) {
                    return true;
                }
            }
        }
        return false;
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
