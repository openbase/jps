/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

/**
 *
 * @author mpohling
 */
public class JPServiceRuntimeException extends RuntimeException {

	public JPServiceRuntimeException(String message, JPServiceException cause) {
		super(message, cause);
	}
}
