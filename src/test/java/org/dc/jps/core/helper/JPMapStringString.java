/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.core.helper;

import org.dc.jps.preset.AbstractJPMapStringString;

/**
 *
 * @author <a href="mailto:mpohling@cit-ec.uni-bielefeld.de">Divine Threepwood</a>
 */
public class JPMapStringString extends AbstractJPMapStringString {

    public static final String[] COMMAND_IDENTIFIER = {"--test-map"};
    
    public JPMapStringString() {
        super(COMMAND_IDENTIFIER);
    }

    @Override
    public String getDescription() {
        return "JPMapStringString description.";
    }
}
