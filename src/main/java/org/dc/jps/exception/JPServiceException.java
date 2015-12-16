/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dc.jps.exception;

/**
 *
 * @author mpohling
 */
public class JPServiceException extends Exception {

    public JPServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JPServiceException(Throwable cause) {
        super(cause);
    }

    public JPServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public JPServiceException(String message) {
        super(message);
    }

    public JPServiceException() {
    }

}
