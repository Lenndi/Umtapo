package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.SubscriptionDao;
import org.lendi.umtapo.dto.SubscriptionDto;
import org.lendi.umtapo.entity.Subscription;
import org.lendi.umtapo.exception.BadSubscriptionDateException;
import org.lendi.umtapo.mapper.SubscriptionMapper;
import org.lendi.umtapo.service.specific.SubscriptionService;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

/**
 * The type Subscription service.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;
    private final SubscriptionMapper subscriptionMapper;
    private final JpaRepository<Subscription, Integer> repository;
    private final SolrBorrowerService solrBorrowerService;

    /**
     * Instantiates a new Subscription service.
     *
     * @param subscriptionDao                  the subscription dao
     * @param subscriptionMapper               the subscription mapper
     * @param subscriptionIntegerJpaRepository the subscription integer jpa repository
     * @param solrBorrowerService              the solr borrower service
     */
    public SubscriptionServiceImpl(
            SubscriptionDao subscriptionDao,
            SubscriptionMapper subscriptionMapper,
            JpaRepository<Subscription, Integer> subscriptionIntegerJpaRepository,
            SolrBorrowerService solrBorrowerService
    ) {
        Assert.notNull(subscriptionDao);
        Assert.notNull(subscriptionMapper);
        Assert.notNull(subscriptionIntegerJpaRepository);
        Assert.notNull(solrBorrowerService);

        this.subscriptionDao = subscriptionDao;
        this.subscriptionMapper = subscriptionMapper;
        this.repository = subscriptionIntegerJpaRepository;
        this.solrBorrowerService = solrBorrowerService;
    }

    @Override
    public Subscription findOne(Integer id) {
        return this.repository.findOne(id);
    }

    @Override
    public List<Subscription> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.repository.delete(id);
    }

    @Override
    public Boolean exists(Integer id) {
        return this.repository.exists(id);
    }

    @Override
    @Transactional
    public Subscription save(Subscription subscription) throws BadSubscriptionDateException {
        if (subscription.getEnd().isBefore(subscription.getStart())) {
            throw new BadSubscriptionDateException("Subscription end date cannot be after start date.");
        }

        List<Subscription> subscriptions = this.subscriptionDao.retrieveByDatesIncludedInRange(
                subscription.getStart(),
                subscription.getEnd(),
                subscription.getBorrower().getId());
        if (subscriptions.size() > 0) {
            throw new BadSubscriptionDateException("Start date or end date is in an existing subscription date range.");
        }

        subscription = this.repository.save(subscription);

        Integer borrowerId = subscription.getBorrower().getId();
        Subscription latestSubscription = this.subscriptionDao.findFirstByBorrowerIdOrderByStartDesc(borrowerId);

        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setSubscriptionStart(Date.from(latestSubscription.getStart().toInstant()));
        borrowerDocument.setSubscriptionEnd(Date.from(latestSubscription.getEnd().toInstant()));
        borrowerDocument.setLibraryId(latestSubscription.getLibrary().getId());
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return subscription;
    }

    @Override
    public SubscriptionDto saveDto(SubscriptionDto subscriptionDto) throws BadSubscriptionDateException {
        Subscription subscription = this.subscriptionMapper.mapSubscriptionDtoToSubscription(subscriptionDto);
        subscription = this.save(subscription);

        return this.subscriptionMapper.mapSubscriptionToSubscriptionDto(subscription);
    }
}
