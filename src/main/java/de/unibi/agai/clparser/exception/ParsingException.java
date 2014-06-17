/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.agai.clparser.exception;

import org.apache.log4j.Logger;

/**
 *
 * @author
 * mpohling
 */
public class ParsingException extends CLParserException {

	private final Logger LOGGER = Logger.getLogger(getClass());

	public ParsingException(String message) {
		super(message);
	}

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}
}
