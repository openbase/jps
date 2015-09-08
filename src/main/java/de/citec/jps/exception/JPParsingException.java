/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

/**
 *
 * @author mpohling
 */
public class JPParsingException extends JPServiceException {

    public JPParsingException(String message) {
        super(message);
    }

    public JPParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
