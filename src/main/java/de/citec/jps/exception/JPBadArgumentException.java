/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

/**
 *
 * @author mpohling
 */
public class JPBadArgumentException extends JPServiceException {

    public JPBadArgumentException(String message) {
        super(message);
    }

    public JPBadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage() + " caused by: " + getCause().getMessage();
    }
}
