package org.lendi.umtapo.solr.repository;

/**
 * The type Solr repository exception.
 */
public class SolrRepositoryException extends Exception {

    /**
     * Instantiates a new Solr repository exception.
     */
    public SolrRepositoryException() {
        super();
    }

    /**
     * Instantiates a new Solr repository exception.
     *
     * @param message the message
     */
    public SolrRepositoryException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Solr repository exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public SolrRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Solr repository exception.
     *
     * @param cause the cause
     */
    public SolrRepositoryException(Throwable cause) {
        super(cause);
    }
}
