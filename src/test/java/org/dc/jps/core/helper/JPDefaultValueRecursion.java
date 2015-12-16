/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.core.helper;

import org.dc.jps.core.JPService;
import org.dc.jps.exception.JPNotAvailableException;
import org.dc.jps.preset.AbstractJPDirectory;
import org.dc.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author mpohling
 */
public class JPDefaultValueRecursion extends AbstractJPDirectory {

    public final static String[] COMMAND_IDENTIFIERS = {"--default-file"};

    public JPDefaultValueRecursion() {
        super(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.On);
    }

    @Override
    protected File getPropertyDefaultValue() {
        try {
            return JPService.getProperty(JPChildDirectory.class).getValue();
        } catch (JPNotAvailableException ex) {
            JPService.printError("Could not load default value!", ex);
            return new File("could/not/load/default");
        }
    }

    @Override
    public String getDescription() {
        return "Test JPDefaultValueRecursion.";
    }
}
