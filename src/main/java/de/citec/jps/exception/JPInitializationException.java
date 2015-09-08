/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.jps.exception;

/**
 *
 * @author mpohling
 */
public class JPInitializationException extends JPServiceException {

    public JPInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JPInitializationException(Throwable cause) {
        super(cause);
    }

    public JPInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JPInitializationException(String message) {
        super(message);
    }

}
