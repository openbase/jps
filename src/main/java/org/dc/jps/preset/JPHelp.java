/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.preset;

import org.dc.jps.core.AbstractJavaProperty;
import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPBadArgumentException;
import java.util.List;

/**
 *
 * @author divine
 */
public final class JPHelp extends AbstractJavaProperty<Void> {

    public final static String[] COMMAND_IDENTIFIERS = {"-h", "--help"};
    public final static String[] ARGUMENT_IDENTIFIERS = {};

    public JPHelp() {
        super(COMMAND_IDENTIFIERS);
    }

    @Override
    protected String[] generateArgumentIdentifiers() {
        return ARGUMENT_IDENTIFIERS;
    }

    @Override
    protected Void getPropertyDefaultValue() {
        return null;
    }

    @Override
    protected Void parse(List<String> arguments) throws JPBadArgumentException {
        return null;
    }

    @Override
    protected void loadAction() {
        if (isIdentifiered()) {
            try {
                JPService.printHelp();
            } catch (Exception ex) {
                logger.error("Could not fully generate help page!", ex);
            } finally {
                if (!JPService.testMode()) {
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Print this help screen.";
    }
}
