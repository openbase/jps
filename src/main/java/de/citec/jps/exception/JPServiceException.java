/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author
 * mpohling
 */
public class JPServiceException extends Exception {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public JPServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JPServiceException(Throwable cause) {
		super(cause);
	}

	public JPServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public JPServiceException(String message) {
		super(message);
	}

	public JPServiceException() {
	}
	
}
