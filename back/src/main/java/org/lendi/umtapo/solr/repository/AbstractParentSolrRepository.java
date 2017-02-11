package org.lendi.umtapo.solr.repository;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.lendi.umtapo.solr.document.ParentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * The type Abstract solr repository.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractParentSolrRepository<T extends ParentDocument>
        extends AbstractSolrRepository<T> implements ParentSolrRepository<T> {

    /**
     * Instantiates a new Abstract solr repository.
     *
     * @param solrConfig the solr config
     * @param core       the core
     */
    protected AbstractParentSolrRepository(SolrConfig solrConfig, String core) {
        super(solrConfig, core);
    }

    @Override
    public void save(T document) throws SolrRepositoryException {
        if (document.getId() == null || document.getId().isEmpty()) {
            throw new SolrRepositoryException("Id can't be null for parent document");
        }

        super.save(document);
    }

    @Override
    public Page<T> searchAll(Pageable pageable) throws SolrRepositoryException {
        SolrQuery query = this.queryChildrenWithParent("*");

        return this.getPage(query, pageable);
    }

    @Override
    public int delete(String id) throws SolrRepositoryException {
        UpdateResponse response;
        try {
            this.solr.deleteById(id);
            response = this.solr.commit();
        } catch (final IOException | SolrServerException e) {
            throw new SolrRepositoryException("Can't document with id " + id, e);
        }

        return response.getStatus();
    }

    /**
     * Search on parent and return parents with there children.
     *
     * @param queryStr the query string
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected SolrQuery queryParentWithChildren(String queryStr) throws SolrRepositoryException {
        String documentType = this.getDocumentType();
        String fieldList = String.format("*,[child parentFilter=document_type:%s]", documentType);

        return this.query(queryStr, null, fieldList, null);
    }

    /**
     * Search on children and return parents with there children.
     *
     * @param queryStr the query str
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected SolrQuery queryChildrenWithParent(String queryStr) throws SolrRepositoryException {
        String documentType = this.getDocumentType();
        String filterQuery = String.format("{!parent which=\"document_type:borrower\"}(%s)", queryStr);
        String fieldList = String.format("*,[child parentFilter=document_type:%s]", documentType);

        return this.query("*:*", filterQuery, fieldList, null);
    }

    /**
     * Query parent and children list.
     *
     * @param queryParent   the query parent
     * @param queryChildren the query children
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected SolrQuery queryParentAndChildren(String queryParent, String queryChildren)
            throws SolrRepositoryException {
        String documentType = this.getDocumentType();
        String filterQuery = String.format("{!parent which=\"document_type:borrower\"}(%s)", queryChildren);
        String fieldList = String.format("*,[child parentFilter=document_type:%s]", documentType);

        return this.query(queryParent, filterQuery, fieldList, null);
    }

    private String getDocumentType() throws SolrRepositoryException {
        String documentType;
        try {
            Field documentTypeField = tType.getDeclaredField("DOCUMENT_TYPE");
            documentTypeField.setAccessible(true);
            documentType = (String) documentTypeField.get(null);
        } catch (final IllegalAccessException e) {
            throw new SolrRepositoryException("Can't access to parent document", e);
        } catch (final NoSuchFieldException e) {
            throw new SolrRepositoryException("A parent document bean should have a DOCUMENT_TYPE constant", e);
        }

        return documentType;
    }
}
