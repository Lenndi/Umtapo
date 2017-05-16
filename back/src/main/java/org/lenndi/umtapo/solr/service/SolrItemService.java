package org.lenndi.umtapo.solr.service;

import org.lenndi.umtapo.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Solr item service interface.
 */
public interface SolrItemService {

    /**
     * Find by id item.
     *
     * @param id the id
     * @return the item
     */
    Item findById(String id);

    /**
     * Save item.
     *
     * @param item the item
     * @return the item
     */
    Item save(Item item);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(String id);

    /**
     * Full search page.
     *
     * @param title           the title
     * @param author          the author
     * @param publisher       the publisher
     * @param id              the id
     * @param publicationDate the publication date
     * @param borrowed        the borrowed
     * @param page            the page
     * @return the page
     */
    Page<Item> fullSearch(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Boolean borrowed,
            Pageable page);
}
