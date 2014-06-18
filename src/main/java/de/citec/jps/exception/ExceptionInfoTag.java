/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

/**
 *
 * @author mpohling
 */
public class ExceptionInfoTag extends JPServiceException {

	public ExceptionInfoTag(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExceptionInfoTag(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionInfoTag(String message) {
		super(message);
	}
	
}
