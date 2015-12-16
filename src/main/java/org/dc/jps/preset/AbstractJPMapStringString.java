/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.exception.JPParsingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
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
