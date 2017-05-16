package org.lenndi.umtapo.exception;

/**
 * Thrown when a subscription start or end date is in conflict with existing subscriptions.
 */
public class SsoIdEqualsPasswordException extends Exception {
    /**
     * Instantiates a new Bad subscription date exception.
     */
    public SsoIdEqualsPasswordException() {
        super();
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     */
    public SsoIdEqualsPasswordException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public SsoIdEqualsPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
