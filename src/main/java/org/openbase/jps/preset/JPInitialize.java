package org.openbase.jps.preset;

/*-
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

import org.openbase.jps.core.JPService;
import org.openbase.jps.exception.JPNotAvailableException;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.de">Tamino Huxohl</a>
 */
public class JPInitialize extends AbstractJPBoolean {

    public final static String[] COMMAND_IDENTIFIERS = {"--init"};

    public JPInitialize() {
        super(COMMAND_IDENTIFIERS);
    }

    /**
     * @return true if JPS is in test mode or JPReset is enabled.
     */
    @Override
    protected Boolean getPropertyDefaultValue() {
        try {
            return JPService.testMode() || JPService.getProperty(JPReset.class).getValue();
        } catch (JPNotAvailableException ex) {
            addErrorReport(ex, ValueType.PropertyDefault);
            return null;
        }
    }

    @Override
    public String getDescription() {
        return "Initialize a new instance of the internal settings.";
    }
}
