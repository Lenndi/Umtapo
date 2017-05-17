package org.lenndi.umtapo.solr.exception;

/**
 * The type Invalid record exception.
 */
public class InvalidRecordException extends Exception {

    /**
     * Instantiates a new Invalid record exception.
     *
     * @param message the message
     */
    public InvalidRecordException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid record exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Invalid record exception.
     *
     * @param cause the cause
     */
    public InvalidRecordException(Throwable cause) {
        super(cause);
    }
}
