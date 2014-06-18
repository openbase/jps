/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

/**
 *
 * @author mpohling
 */
public class PropertyInitializationException extends JPServiceException {

	public PropertyInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PropertyInitializationException(Throwable cause) {
		super(cause);
	}

	public PropertyInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyInitializationException(String message) {
		super(message);
	}
	
}
