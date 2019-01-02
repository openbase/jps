package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2019 openbase.org
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

import org.openbase.jps.exception.JPParsingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public abstract class AbstractJPMapStringString extends AbstractJPMap<String, String> {

    public AbstractJPMapStringString(String[] commandIdentifier) {
        super(commandIdentifier);
    }

    @Override
    protected Map<String, String> parse(List<String> arguments) throws Exception {

        checkArgumentCountMin(1);
        
        Map<String, String> keyValueMap = new HashMap<>();
        
        for (String argument : arguments) {
            if (!argument.contains(AbstractJPMap.KEY_VALUE_SEPARATOR)) {
                throw new JPParsingException("KeyValueSeparator[" + AbstractJPMap.KEY_VALUE_SEPARATOR + "] is missing for Argument[" + argument + "].");
            }
            
            String[] split = argument.split(AbstractJPMap.KEY_VALUE_SEPARATOR);
            
            if (split.length != 2) {
                throw new JPParsingException("Argument[" + argument + "] does not contain a valid key value mapping!");
            }
            
            keyValueMap.put(split[0], split[1]);
        }
        return keyValueMap;
    }
}
