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

	private JPServiceException innerCLParserException;
	
	public JPServiceRuntimeException(String message, JPServiceException cause) {
		super(message, cause);
		this.innerCLParserException = cause;
	}

	public JPServiceException getInnerCLParserException() {
		return innerCLParserException;
	}
}
