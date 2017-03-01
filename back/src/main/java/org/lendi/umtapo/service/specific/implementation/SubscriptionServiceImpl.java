package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.SubscriptionDao;
import org.lendi.umtapo.dto.SubscriptionDto;
import org.lendi.umtapo.entity.Subscription;
import org.lendi.umtapo.mapper.SubscriptionMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.SubscriptionService;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * The type Subscription service.
 */
@Service
public class SubscriptionServiceImpl extends AbstractGenericService<Subscription, Integer>
        implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;
    private final SubscriptionMapper subscriptionMapper;
    private final SolrBorrowerService solrBorrowerService;

    /**
     * Instantiates a new Subscription service.
     *
     * @param subscriptionDao     the subscription dao
     * @param subscriptionMapper  the subscription mapper
     * @param solrBorrowerService the solr borrower service
     */
    public SubscriptionServiceImpl(
            SubscriptionDao subscriptionDao,
            SubscriptionMapper subscriptionMapper,
            SolrBorrowerService solrBorrowerService
    ) {
        Assert.notNull(subscriptionDao);
        Assert.notNull(subscriptionMapper);
        Assert.notNull(solrBorrowerService);

        this.subscriptionDao = subscriptionDao;
        this.subscriptionMapper = subscriptionMapper;
        this.solrBorrowerService = solrBorrowerService;
    }

    @Override
    public Subscription save(Subscription subscription) {
        subscription = super.save(subscription);

        Integer borrowerId = subscription.getBorrower().getId();
        Subscription latestSubscription = this.subscriptionDao.findFirstByBorrowerIdOrderByStartDesc(borrowerId);

        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setSubscriptionStart(latestSubscription.getStart());
        borrowerDocument.setSubscriptionEnd(latestSubscription.getEnd());
        borrowerDocument.setLibraryId(latestSubscription.getLibrary().getId());
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return subscription;
    }

    @Override
    public SubscriptionDto saveDto(SubscriptionDto subscriptionDto) {
        Subscription subscription = this.subscriptionMapper.mapSubscriptionDtoToSubscription(subscriptionDto);
        subscription = this.save(subscription);

        return this.subscriptionMapper.mapSubscriptionToSubscriptionDto(subscription);
    }
}
