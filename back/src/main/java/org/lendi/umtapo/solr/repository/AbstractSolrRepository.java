package org.lendi.umtapo.solr.repository;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The type Abstract solr repository.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractSolrRepository<T> implements SolrRepository<T> {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 50;

    /**
     * The Solr.
     */
    protected final SolrClient solr;
    /**
     * The T type.
     */
    protected final Class<T> tType;

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
        } catch (final IOException | SolrServerException e) {
            throw new SolrRepositoryException("Can't add document to solr", e);
        }
    }

    @Override
    public Page<T> searchAll(Pageable pageable) throws SolrRepositoryException {
        SolrQuery query = this.query("*");

        return this.getPage(query, pageable);
    }

    /**
     * Query list.
     *
     * @param queryStr the query str
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected SolrQuery query(String queryStr) throws SolrRepositoryException {
        return this.query(queryStr, null, null, null);
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
    protected SolrQuery query(String queryStr, String optFilter, String optFields, Map<String, String> extraParams)
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

        return query;
    }

    /**
     * Gets list.
     *
     * @param query the query
     * @return the list
     * @throws SolrRepositoryException the solr repository exception
     */
    protected List<T> getList(SolrQuery query) throws SolrRepositoryException {
        QueryResponse response = this.processQuery(query);

        return response.getBeans(this.tType);
    }

    /**
     * Gets page.
     *
     * @param query    the query
     * @param pageable the pageable
     * @return the page
     * @throws SolrRepositoryException the solr repository exception
     */
    protected Page<T> getPage(SolrQuery query, Pageable pageable) throws SolrRepositoryException {
        if (pageable == null) {
            pageable = new PageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
        }

        query.setStart(pageable.getPageNumber());
        query.setRows(pageable.getPageSize());

        if (pageable.getSort() != null) {
            for (final Sort.Order order : pageable.getSort()) {
                SolrQuery.ORDER translatedOrder;
                if (order.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    translatedOrder = SolrQuery.ORDER.asc;
                } else {
                    translatedOrder = SolrQuery.ORDER.desc;
                }
                query.addSort(order.getProperty(), translatedOrder);
            }
        }

        QueryResponse response = this.processQuery(query);

        return new PageImpl<>(response.getBeans(this.tType), pageable, response.getResults().getNumFound());
    }

    private QueryResponse processQuery(SolrQuery query) throws SolrRepositoryException {
        QueryResponse response;
        try {
            response = solr.query(query);
        } catch (final SolrServerException | IOException e) {
            throw new SolrRepositoryException("Can't execute query : " + query.toString(), e);
        }

        return response;
    }
}
