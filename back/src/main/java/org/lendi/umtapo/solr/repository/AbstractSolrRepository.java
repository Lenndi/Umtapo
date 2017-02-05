package org.lendi.umtapo.solr.repository;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.springframework.core.GenericTypeResolver;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSolrRepository<T> implements SolrRepository<T> {

    private SolrClient solr;
    private final Class<T> tType;

    protected AbstractSolrRepository(SolrConfig solrConfig, String core) throws SolrServerException {
        this.solr = solrConfig.solrClient(core);

        this.tType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractSolrRepository.class);
    }

    @Override
    public void save(T document) throws IOException, SolrServerException {
        this.solr.addBean(document);
        this.solr.commit();
    }

    @Override
    public void delete(String id) throws IOException, SolrServerException {
        this.solr.deleteById(id);
        this.solr.commit();
    }

    protected List<T> query(String queryStr) throws IOException, SolrServerException {
        return this.query(queryStr, null, null, null);
    }

    protected List<T> queryWithChild(String queryStr) throws SolrServerException, IOException {
        String documentType;
        try {
            documentType = (String) tType.getField("DOCUMENT_TYPE").get(null);
        } catch (final IllegalAccessException e) {
            throw new SolrServerException("Can't access to parent document");
        } catch (final NoSuchFieldException e) {
            throw new SolrServerException("A parent document bean should have a DOCUMENT_TYPE constant", e);
        }

        String optFields = "*,[child parentFilter=document_type:" + documentType + "]";

        return this.query(queryStr, null, optFields, null);
    }

    protected List<T> query(String queryStr, String optFilter, String optFields, Map<String, String> extraParams)
            throws SolrServerException, IOException {
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

        QueryResponse response = solr.query(query);

        return response.getBeans(this.tType);
    }
}
