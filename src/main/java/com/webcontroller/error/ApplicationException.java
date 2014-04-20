package com.webcontroller.error;

/**
 * Created by Arkkienkeli on 02.03.14.
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -1998731323188051952L;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

}