package org.lenndi.umtapo.rest;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Error handler for rest controllers.
 */
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param message the message
     * @param errors  the errors
     */
    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param message the message
     * @param error   the error
     */
    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Sets errors.
     *
     * @param errors the errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
