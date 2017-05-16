package org.lenndi.umtapo.solr.repository;

import org.lenndi.umtapo.solr.document.ItemDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Solr item repository.
 */
public interface SolrItemRepository extends SolrCrudRepository<ItemDocument, String> {

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
    @Query("mainTitle:*?0* AND _name_:*?1* AND editorName:*?2* AND id:?3* AND publicationDate:?4* AND borrowed:?5")
    Page<ItemDocument> fullSearch(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            String borrowed,
            Pageable page);
}
