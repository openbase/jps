/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.exception;

/**
 *
 * @author mpohling
 */
public class CLParserRuntimeException extends RuntimeException {

	private CLParserException innerCLParserException;
	
	public CLParserRuntimeException(String message, CLParserException cause) {
		super(message, cause);
		this.innerCLParserException = cause;
	}

	public CLParserException getInnerCLParserException() {
		return innerCLParserException;
	}
}
