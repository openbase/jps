/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2018 openbase.org
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
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public final class JPHelp extends AbstractJavaProperty<Void> {

    public final static String[] COMMAND_IDENTIFIERS = {"-h", "--help"};
    public final static String[] ARGUMENT_IDENTIFIERS = {};

    public JPHelp() {
        super(COMMAND_IDENTIFIERS);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Void getPropertyDefaultValue() {
        return null;
    }

    @Override
    protected Void parse(List<String> arguments) throws JPBadArgumentException {
        return null;
    }

    @Override
    public String getDescription() {
        return "Print this help screen.";
    }
}
