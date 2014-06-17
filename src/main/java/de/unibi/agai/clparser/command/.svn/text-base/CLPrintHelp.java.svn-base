/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.exception.BadArgumentException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author divine
 */
public class CLPrintHelp extends AbstractRunCommand<Void> {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public final static String[] COMMAND_IDENTIFIERS = {"-h", "--help"};
	public final static String[] ARGUMENT_IDENTIFIERS = {};

	public CLPrintHelp() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}

	@Override
	protected void validate() throws Exception {
		if(isIdentifiered()) {
			CLParser.printHelp();
			System.exit(0);
		}
	}
	
	@Override
	protected Void getCommandDefaultValue() {
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
