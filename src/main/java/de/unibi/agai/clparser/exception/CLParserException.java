/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.exception;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

/**
 *
 * @author
 * mpohling
 */
public class CLParserException extends Exception {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public CLParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CLParserException(Throwable cause) {
		super(cause);
	}

	public CLParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public CLParserException(String message) {
		super(message);
	}

	public CLParserException() {
	}
	
}
