package org.openbase.log;

/*-
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2021 openbase.org
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
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.spi.ContextAwareBase;

import java.util.HashMap;
import java.util.Map;

public class OpenBaseLogConfigurator extends ContextAwareBase implements Configurator {

    public void configure(LoggerContext context) {
        addInfo("Setting up default configuration.");

        // load rule registry
        Map<String, String> ruleRegistry = (Map) context.getObject(CoreConstants.PATTERN_RULE_REGISTRY);
        if (ruleRegistry == null) {
            ruleRegistry = new HashMap<>();
        }

        // setup openbase coloring conversion
        ruleRegistry.put("messageHighlighting", "org.openbase.log.OpenBaseLogbackMessageColorTheme");
        ruleRegistry.put("loggerHighlighting", "org.openbase.log.OpenBaseLogbackLoggerColorTheme");
        context.putObject(CoreConstants.PATTERN_RULE_REGISTRY, ruleRegistry);

        // setup default console appender.
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(context);
        consoleAppender.setName("STDOUT");

        /**
         * On Windows machines setting withJansi to true enables ANSI color code interpretation
         * by the Jansi library. This requires org.fusesource.jansi:jansi:1.8 on the class path.
         * Note that Unix-based operating systems such as Linux and Mac OS X support ANSI color
         * codes by default.
         */
        consoleAppender.setWithJansi(true);

        // setup colored default pattern
        PatternLayout layout = new PatternLayout();
        layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %loggerHighlighting(%-5level) %cyan(%logger{15}) %messageHighlighting(%msg%n)");
        layout.setContext(context);
        layout.start();

        // setup encoder
        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
        encoder.setContext(context);
        encoder.setLayout(layout);
        consoleAppender.setEncoder(encoder);

        // start console appender
        consoleAppender.start();

        // setup root logger
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(consoleAppender);
    }
}
