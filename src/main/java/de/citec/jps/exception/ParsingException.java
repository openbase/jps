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
public class ParsingException extends JPServiceException {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public ParsingException(String message) {
		super(message);
	}

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}
}
