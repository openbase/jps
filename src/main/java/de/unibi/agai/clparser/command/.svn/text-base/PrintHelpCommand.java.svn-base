/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.agai.clparser.command;

import de.unibi.agai.clparser.AbstractRunCommand;
import de.unibi.agai.clparser.CLParser;
import org.apache.log4j.Logger;

/**
 *
 * @author divine
 */
public class PrintHelpCommand extends AbstractRunCommand<Void> {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public final static String[] COMMAND_IDENTIFIERS = {"-h", "--help"};
	public final static String[] ARGUMENT_IDENTIFIERS = {};
	public final static Void[] DEFAULT_VALUES = {};

	public PrintHelpCommand() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, DEFAULT_VALUES);
	}

	@Override
	protected void validate() throws Exception {
		if(isIdentifiered()) {
			CLParser.printHelp();
			System.exit(0);
		}
	}

	@Override
	protected Void parse(String arg) throws Exception {
		return null;
	}

	@Override
	public String getDescription() {
		return "Print this help screen.";
	}
}
