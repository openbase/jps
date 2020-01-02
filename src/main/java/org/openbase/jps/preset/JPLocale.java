package org.openbase.jps.preset;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2020 openbase.org
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
import org.openbase.jps.exception.JPBadArgumentException;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPLocale extends AbstractJavaProperty<Locale> {

    public final static String[] COMMAND_IDENTIFIERS = {"--locale"};
    public final static String[] ARGUMENT_IDENTIFIERS = {Locale.class.getSimpleName().toUpperCase()};

    public JPLocale() {
        super(COMMAND_IDENTIFIERS);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Locale getPropertyDefaultValue() {
        return Locale.getDefault();
    }

    @Override
    protected Locale parse(List<String> arguments) throws JPBadArgumentException {
        String oneArgumentResult = getOneArgumentResult();
        try {
            final Locale locale = parseLocale(oneArgumentResult);
            if (!isValid(locale)) {
                throw new JPBadArgumentException("Given Language is unknown!");
            }
            return locale;
        } catch (NullPointerException ex) {
            throw new JPBadArgumentException("Could not load language!", ex);
        }
    }

    @Override
    public String getDescription() {
        return "Specifies the language to use for this application.";
    }

    private Locale parseLocale(String locale) {
        String[] elements = locale.split("_");
        switch (elements.length) {
            case 1:
                return new Locale(elements[0]);
            case 2:
                return new Locale(elements[0], elements[1]);
            case 3:
                return new Locale(elements[0], elements[1], elements[2]);
            default:
                throw new IllegalArgumentException("Invalid locale: " + locale);
        }
    }

    private boolean isValid(final Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException ex) {
            return false;
        }
    }
}
