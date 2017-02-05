package org.lendi.umtapo.solr.repository;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.springframework.core.GenericTypeResolver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * The type Abstract solr repository.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractSolrRepository<T> implements SolrRepository<T> {

    private final SolrClient solr;
    private final Class<T> tType;

    /**
     * Instantiates a new Abstract solr repository.
     *
     * @param solrConfig the solr config
     * @param core       the core
     */
    protected AbstractSolrRepository(SolrConfig solrConfig, String core) {
        this.solr = solrConfig.solrClient(core);

        this.tType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractSolrRepository.class);
    }

    @Override
    public void save(T document) throws SolrRepositoryException {
        try {
            this.solr.addBean(document);
            this.solr.commit();
        } catch (IOException | SolrServerException e) {
            throw new SolrRepositoryException("Can't add document to solr", e);
        }
    }

    @Override
    public void delete(String id) throws SolrRepositoryException {
        try {
            this.solr.deleteById(id);
            this.solr.commit();
        } catch (IOException | SolrServerException e) {
            throw new SolrRepositoryException("Can't document with id " + id, e);
        }
    }

    /**
     * Query list.
     *
     * @param queryStr the query str
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected List<T> query(String queryStr) throws SolrRepositoryException {
        return this.query(queryStr, null, null, null);
    }

    /**
     * Query parent with children list.
     *
     * @param queryStr the query str
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected List<T> queryParentWithChildren(String queryStr) throws SolrRepositoryException {
        String documentType = this.getDocumentType();
        String fieldList = String.format("*,[child parentFilter=document_type:%s]", documentType);

        return this.query(queryStr, null, fieldList, null);
    }

    /**
     * Query children with parent list.
     *
     * @param queryStr the query str
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected List<T> queryChildrenWithParent(String queryStr) throws SolrRepositoryException {
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
    protected List<T> queryParentAndChildren(String queryParent, String queryChildren) throws SolrRepositoryException {
        String documentType = this.getDocumentType();
        String filterQuery = String.format("{!parent which=\"document_type:borrower\"}(%s)", queryChildren);
        String fieldList = String.format("*,[child parentFilter=document_type:%s]", documentType);

        return this.query(queryParent, filterQuery, fieldList, null);
    }

    /**
     * Query list.
     *
     * @param queryStr    the query str
     * @param optFilter   the opt filter
     * @param optFields   the opt fields
     * @param extraParams the extra params
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected List<T> query(String queryStr, String optFilter, String optFields, Map<String, String> extraParams)
            throws SolrRepositoryException {
        SolrQuery query = new SolrQuery(queryStr);
        if (null != optFilter) {
            query.addFilterQuery(optFilter);
        }
        if (null != optFields) {
            query.setParam("fl", optFields);
        } else {
            query.addField("*");
        }
        if (null != extraParams) {
            for (final Map.Entry<String, String> param : extraParams.entrySet()) {
                query.set(param.getKey(), param.getValue());
            }
        }

        QueryResponse response;
        try {
            response = solr.query(query);
        } catch (SolrServerException | IOException e) {
            throw new SolrRepositoryException("Can't execute query : " + query.toString(), e);
        }

        return response.getBeans(this.tType);
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
