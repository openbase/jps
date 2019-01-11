package org.openbase.jps.preset;

/*-
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
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.util.List;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jps.exception.JPServiceException;
import org.openbase.jps.preset.JPLogLevel.LogLevel;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPLogLevel extends AbstractJPEnum<LogLevel> {

    public final static String[] COMMAND_IDENTIFIERS = {"--visual-debug"};

    enum LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR, OFF
    }

    public JPLogLevel() {
        super(COMMAND_IDENTIFIERS);
    }
    
    @Override
    protected LogLevel getPropertyDefaultValue() throws JPNotAvailableException {
        try {
            return LogLevel.valueOf(getRootLogger().getLevel().toString());
        } catch (Exception ex) {
            throw new JPNotAvailableException(getClass(), ex);
        }
    }

    @Override
    public String getDescription() {
        return "Changes the log level to the given one. Valid values are [].";
    }

    private Logger getRootLogger() throws JPServiceException {
        try {
            return (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        } catch (Exception ex) {
            throw new JPServiceException("RootLogger is not available!", ex);
        }
    }

    @Override
    protected LogLevel parse(List<String> arguments) throws JPServiceException {
        LogLevel logLevel = super.parse(arguments);
        getRootLogger().setLevel(Level.valueOf(logLevel.name()));
        return logLevel;
    }
}
