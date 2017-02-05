package org.lendi.umtapo.solr.repository;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface SolrRepository<T> {

    void save(T document) throws IOException, SolrServerException;

    void delete(String id) throws IOException, SolrServerException;
}
