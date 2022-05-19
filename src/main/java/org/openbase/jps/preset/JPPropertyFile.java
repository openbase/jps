/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.jps.preset;

/*
 * #%L
 * JPS
 * %%
 * Copyright (C) 2014 - 2022 openbase.org
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

import org.openbase.jps.core.JPService;
import org.openbase.jps.tools.FileHandler;
import java.io.File;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class JPPropertyFile extends AbstractJPFile {

	public final static String[] COMMAND_IDENTIFIERS = {"--properties"};

	public static FileHandler.ExistenceHandling existenceHandling = FileHandler.ExistenceHandling.CanExist;
	public static FileHandler.AutoMode autoMode = FileHandler.AutoMode.On;
	
	public JPPropertyFile() {
		super(COMMAND_IDENTIFIERS, existenceHandling, autoMode);
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(System.getProperty("user.home")+System.getProperty("path.separator")+".JPService"+System.getProperty("path.separator")+JPService.getApplicationName()+".properties");
	}

	@Override
	public String getDescription() {
		return "Set the neutral posture URI. URI is absolute and prefix independent handled.";
	}
}
