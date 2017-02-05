package org.lendi.umtapo.solr.repository;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.lendi.umtapo.solr.configuration.SolrConfig;
import org.lendi.umtapo.solr.document.AddressDocument;
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
    }

    @Override
    public void delete(String id) throws IOException, SolrServerException {
        this.solr.deleteById(id);
    }

    protected List<T> query(String queryStr) throws IOException, SolrServerException {
        return this.query(queryStr, null, null, null);
    }

    protected List<T> queryWithChild(String queryStr) throws SolrServerException, IllegalAccessException, IOException {
        String documentType;
        try {
            documentType = (String) tType.getField("DOCUMENT_TYPE").get(null);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessException("Can't access to parent document");
        } catch (final NoSuchFieldException e) {
            throw new SolrServerException("A parent document bean should have a DOCUMENT_TYPE constant", e);
        }

        Map<String, String> params = new LinkedHashMap<>();
        params.put("parent_filter", "document_type:" + documentType );

        return this.query(queryStr, null, "*,[child parentFilter=$parent_filter]", params);
    }

    protected List<T> query(String queryStr, String optFilter, String optFields, Map<String, String> extraParams)
            throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery(queryStr);
        if (optFilter != null) {
            query.addFilterQuery(optFilter);
        }
        if (optFields != null) {
            query.setParam("fl", optFields);
        } else {
            query.addField("*");
        }
        if (extraParams != null) {
            extraParams.entrySet().forEach(param -> query.set(param.getKey(), param.getValue()));
        }

        QueryResponse response;
        try {
            response = this.solr.query(query);
        } catch (final SolrServerException e) {
            throw new SolrServerException("Query cannot be resolved !", e);
        } catch (final IOException e) {
            throw new IOException("Query cannot be resolved !", e);
        }

        return response.getBeans(this.tType);
    }
}
