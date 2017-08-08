package org.openbase.jps.core.helper;

import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.preset.AbstractJPString;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.de">Tamino Huxohl</a>
 */
public class JPTestProperty extends AbstractJPString {

    public static final String[] COMMAND_IDENTIFIERS = {"-t", "--test"};

    public JPTestProperty() {
        super(COMMAND_IDENTIFIERS);
    }

    @Override
    protected String getPropertyDefaultValue() throws JPNotAvailableException {
        return "Test";
    }

    @Override
    public String getDescription() {
        return "This is just a property test!";
    }    
}
