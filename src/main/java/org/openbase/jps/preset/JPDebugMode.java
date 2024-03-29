/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2023 openbase.org
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

import org.openbase.jps.exception.JPValidationException;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPDebugMode extends AbstractJPBoolean {
	
	public final static String[] COMMAND_IDENTIFIERS = {"-d", "--debug"};

	public JPDebugMode() {
		super(COMMAND_IDENTIFIERS);
	}
	
	@Override
	protected void validate() throws JPValidationException {
		if(getValue()) {
			logger.info("Debug mode enabled!");
		}
	}

	@Override
	public String getDescription() {
		return "Enables the debug mode.";
	}
}
