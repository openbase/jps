package org.openbase.jps.preset;

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
        String number = arg.substring(0, arg.length() - 2);
        
        TimeUnit timeunit;
        switch(unit) {
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
