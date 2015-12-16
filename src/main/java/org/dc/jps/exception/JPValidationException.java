/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.exception;

/**
 *
 * @author mpohling
 */
public class JPValidationException extends JPServiceException {

	public JPValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JPValidationException(Throwable cause) {
		super(cause);
	}

	public JPValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public JPValidationException(String message) {
		super(message);
	}
}
