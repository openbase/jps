package org.openbase.log;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2022 openbase.org
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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class OpenBaseLogbackMessageColorTheme extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        switch (event.getLevel().toInt()) {
            case Level.INFO_INT:
                return ANSIConstants.DEFAULT_FG;
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;
            case Level.ERROR_INT:
                return ANSIConstants.BOLD + ANSIConstants.RED_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}
