package org.lenndi.umtapo.exception;

/**
 * Thrown when a subscription start or end date is in conflict with existing subscriptions.
 */
public class BadSubscriptionDateException extends Exception {
    /**
     * Instantiates a new Bad subscription date exception.
     */
    public BadSubscriptionDateException() {
        super();
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     */
    public BadSubscriptionDateException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadSubscriptionDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
