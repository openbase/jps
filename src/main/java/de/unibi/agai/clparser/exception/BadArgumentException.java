/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author
 * mpohling
 */
public class BadArgumentException extends CLParserException {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public BadArgumentException(String message) {
		super(message);
	}

	public BadArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getLocalizedMessage() {
		return getMessage() + " caused by: " + getCause().getMessage();
	}
}

