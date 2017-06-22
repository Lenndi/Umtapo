package org.lenndi.umtapo.exception;

/**
 * Thrown when a subscription start or end date is in conflict with existing subscriptions.
 */
public class NotLoannableException extends Exception {
    /**
     * Instantiates a new Bad subscription date exception.
     */
    public NotLoannableException() {
        super();
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     */
    public NotLoannableException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NotLoannableException(String message, Throwable cause) {
        super(message, cause);
    }
}
