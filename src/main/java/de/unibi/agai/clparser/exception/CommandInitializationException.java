/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.exception;

/**
 *
 * @author mpohling
 */
public class CommandInitializationException extends CLParserException {

	public CommandInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandInitializationException(Throwable cause) {
		super(cause);
	}

	public CommandInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandInitializationException(String message) {
		super(message);
	}
	
}
