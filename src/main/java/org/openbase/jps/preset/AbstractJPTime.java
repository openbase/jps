package org.openbase.jps.preset;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2017 openbase.org
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openbase.jps.core.AbstractJavaProperty;
import org.openbase.jps.exception.JPBadArgumentException;

/**
 *
 * @author <a href="mailto:thuxohl@techfak.uni-bielefeld.de">Tamino Huxohl</a>
 */
public abstract class AbstractJPTime extends AbstractJavaProperty<Long> {

    public final static String[] ARGUMENT_IDENTIFIERS = {"LONG[d/h/m/s/c/n]"};

    public AbstractJPTime(final String[] commandIdentifiers) {
        super(commandIdentifiers);
    }

    @Override
    public String getDescription() {
        return getTimeDescription() + "Unit explanation: no unit means milliseconds, d is for days, h for hours, m for minutes, s for seconds, c for microseconds and n is for nanoseconds.";
    }

    public abstract String getTimeDescription();

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Long parse(List<String> arguments) throws JPBadArgumentException {
        String arg = getOneArgumentResult();
        char unit = arg.toLowerCase().charAt(arg.length() - 1);
        String number = arg.substring(0, arg.length() - 1);

        TimeUnit timeunit;
        switch (unit) {
            case 'd':
                timeunit = TimeUnit.DAYS;
                break;
            case 'h':
                timeunit = TimeUnit.HOURS;
                break;
            case 'm':
                timeunit = TimeUnit.MINUTES;
                break;
            case 's':
                timeunit = TimeUnit.SECONDS;
                break;
            case 'c':
                timeunit = TimeUnit.MICROSECONDS;
                break;
            case 'n':
                timeunit = TimeUnit.NANOSECONDS;
                break;
            default:
                number = arg;
                timeunit = TimeUnit.MILLISECONDS;
        }

        return TimeUnit.MILLISECONDS.convert(Long.parseLong(number), timeunit);
    }

}
