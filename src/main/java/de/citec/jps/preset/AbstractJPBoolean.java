/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.preset;

import de.citec.jps.core.AbstractJavaProperty;
import de.citec.jps.exception.BadArgumentException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author divine
 */
public abstract class AbstractJPBoolean extends AbstractJavaProperty<Boolean> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public final static String[] ARGUMENT_IDENTIFIERS = {"BOOLEAN"};

	public AbstractJPBoolean(String[] COMMAND_IDENTIFIERS) {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS);
	}

	@Override
	protected Boolean parse(List<String> arguments) throws BadArgumentException {
		checkArgumentCount(0, 1);
		if (arguments.isEmpty()) { // parse as flag
			return true;
		}
		return Boolean.parseBoolean(arguments.get(0)); // parse as argument
	}
	
	@Override
	protected Boolean getPropertyDefaultValue() {
		return false;
	}
}
