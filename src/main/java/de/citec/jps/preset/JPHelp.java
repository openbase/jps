/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.core.JPService;
import de.citec.jps.exception.BadArgumentException;
import java.util.List;

/**
 *
 * @author divine
 */
public class JPHelp extends AbstractJavaProperty<Void> {

	public final static String[] COMMAND_IDENTIFIERS = {"-h", "--help"};
	public final static String[] ARGUMENT_IDENTIFIERS = {};

	public JPHelp() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}

	@Override
	protected void validate() throws Exception {
		if(isIdentifiered()) {
			JPService.printHelp();
			System.exit(0);
		}
	}
	
	@Override
	protected Void getPropertyDefaultValue() {
		return null;
	}

	@Override
	protected Void parse(List<String> arguments) throws BadArgumentException {
		return null;
	}

	@Override
	public String getDescription() {
		return "Print this help screen.";
	}
}
