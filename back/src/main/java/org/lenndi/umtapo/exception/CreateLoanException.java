package org.lenndi.umtapo.exception;

/**
 * Thrown when a subscription start or end date is in conflict with existing subscriptions.
 */
public class CreateLoanException extends Exception {
    /**
     * Instantiates a new Bad subscription date exception.
     */
    public CreateLoanException() {
        super();
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     */
    public CreateLoanException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad subscription date exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CreateLoanException(String message, Throwable cause) {
        super(message, cause);
    }
}
