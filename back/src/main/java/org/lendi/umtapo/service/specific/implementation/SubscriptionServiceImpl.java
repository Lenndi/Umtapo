package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.SubscriptionDao;
import org.lendi.umtapo.entity.Subscription;
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
    private final SolrBorrowerService solrBorrowerService;

    /**
     * Instantiates a new Subscription service.
     *
     * @param subscriptionDao     the subscription dao
     * @param solrBorrowerService the solr borrower service
     */
    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao, SolrBorrowerService solrBorrowerService) {
        Assert.notNull(subscriptionDao);
        Assert.notNull(solrBorrowerService);

        this.subscriptionDao = subscriptionDao;
        this.solrBorrowerService = solrBorrowerService;
    }

    @Override
    public Subscription save(Subscription subscription) {
        subscription = super.save(subscription);

        Integer borrowerId = subscription.getBorrower().getId();
        Subscription latestSubscription = this.findLatestSubscriptionByBorrowerId(borrowerId);

        if (latestSubscription.getStart().isBefore(subscription.getStart())) {
            BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());

            borrowerDocument.setSubscriptionStart(subscription.getStart());
            borrowerDocument.setSubscriptionEnd(subscription.getEnd());
            this.solrBorrowerService.saveToIndex(borrowerDocument);
        }

        return subscription;
    }

    @Override
    public Subscription findLatestSubscriptionByBorrowerId(Integer borrowerId) {
        return this.subscriptionDao.findFirstByBorrowerIdOrderByStartDesc(borrowerId);
    }
}
