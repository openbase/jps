/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author mpohling
 */
public class BadArgumentException extends JPServiceException {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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

