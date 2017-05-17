package org.lenndi.umtapo.solr.service.implementation;

import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.mapper.ItemMapper;
import org.lenndi.umtapo.solr.document.ItemDocument;
import org.lenndi.umtapo.solr.repository.SolrItemRepository;
import org.lenndi.umtapo.solr.service.SolrItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Solr item service.
 */
@Service
public class SolrItemServiceImpl implements SolrItemService {

    private final SolrItemRepository itemRepository;
    private final ItemMapper itemMapper;

    /**
     * Instantiates a new Solr item service.
     *
     * @param itemRepository the item repository
     * @param itemMapper     the item mapper
     */
    @Autowired
    public SolrItemServiceImpl(SolrItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public Item findById(String id) {
        return this.itemMapper.mapDocumentToItem(this.itemRepository.findOne(id));
    }

    @Override
    public Item save(Item item) {
        ItemDocument itemDocument = this.itemMapper.mapItemToDocument(item);

        return this.itemMapper.mapDocumentToItem(this.itemRepository.save(itemDocument));
    }

    @Override
    public void delete(String id) {
        this.itemRepository.delete(id);
    }

    @Override
    public Page<Item> fullSearch(
            String title,
            String author,
            String publisher,
            String id,
            String publicationDate,
            Boolean borrowed,
            Pageable page
    ) {
        String borrowedStr = "*";
        if (borrowed != null) {
            borrowedStr = borrowed.toString();
        }

        Page<ItemDocument> itemDocuments =
                this.itemRepository.fullSearch(title, author, publisher, id, publicationDate, borrowedStr, page);

        return this.mapDocumentPageToItemPage(itemDocuments);
    }

    private Page<Item> mapDocumentPageToItemPage(Page<ItemDocument> itemDocumentPage) {
        List<ItemDocument> itemDocuments = itemDocumentPage.getContent();
        List<Item> items = new ArrayList<>();
        itemDocuments.forEach(itemDocument ->
                items.add(this.itemMapper.mapDocumentToItem(itemDocument))
        );
        Pageable pageable = new PageRequest(itemDocumentPage.getNumber(), itemDocumentPage.getSize());

        return new PageImpl<>(items, pageable, itemDocumentPage.getTotalElements());
    }
}
