package org.lenndi.umtapo.service.specific.implementation;

import org.lenndi.umtapo.dao.SubscriptionDao;
import org.lenndi.umtapo.dto.SubscriptionDto;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.entity.Subscription;
import org.lenndi.umtapo.exception.BadSubscriptionDateException;
import org.lenndi.umtapo.mapper.SubscriptionMapper;
import org.lenndi.umtapo.service.specific.LibraryService;
import org.lenndi.umtapo.service.specific.SubscriptionService;
import org.lenndi.umtapo.solr.document.BorrowerDocument;
import org.lenndi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * The type Subscription service.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final int UNDEFINED_FUTUR = 10;

    private final SubscriptionDao subscriptionDao;
    private final SubscriptionMapper subscriptionMapper;
    private final JpaRepository<Subscription, Integer> repository;
    private final SolrBorrowerService solrBorrowerService;
    private final LibraryService libraryService;

    /**
     * Instantiates a new Subscription service.
     *
     * @param subscriptionDao                  the subscription dao
     * @param subscriptionMapper               the subscription mapper
     * @param subscriptionIntegerJpaRepository the subscription integer jpa repository
     * @param solrBorrowerService              the solr borrower service
     * @param libraryService                   the library service
     */
    public SubscriptionServiceImpl(
            SubscriptionDao subscriptionDao,
            SubscriptionMapper subscriptionMapper,
            JpaRepository<Subscription, Integer> subscriptionIntegerJpaRepository,
            SolrBorrowerService solrBorrowerService,
            LibraryService libraryService
    ) {
        this.subscriptionDao = subscriptionDao;
        this.subscriptionMapper = subscriptionMapper;
        this.repository = subscriptionIntegerJpaRepository;
        this.solrBorrowerService = solrBorrowerService;
        this.libraryService = libraryService;
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
        Library latestLibrary = libraryService.findOne(latestSubscription.getLibrary().getId());

        BorrowerDocument borrowerDocument = this.solrBorrowerService.findById(borrowerId.toString());
        borrowerDocument.setSubscriptionStart(Date.from(latestSubscription.getStart().toInstant()));
        borrowerDocument.setSubscriptionEnd(Date.from(latestSubscription.getEnd().toInstant()));
        borrowerDocument.setLibraryId(latestLibrary.getId());
        borrowerDocument.setLibraryName(latestLibrary.getName());
        if (this.isFirstSubscription(subscription)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date());
            calendar.add(Calendar.YEAR, UNDEFINED_FUTUR);

            borrowerDocument.setNbLoans(0);
            borrowerDocument.setTooMuchLoans(false);
            borrowerDocument.setOlderReturn(calendar.getTime());
        }
        this.solrBorrowerService.saveToIndex(borrowerDocument);

        return subscription;
    }

    @Override
    public SubscriptionDto saveDto(SubscriptionDto subscriptionDto) throws BadSubscriptionDateException {
        Subscription subscription = this.subscriptionMapper.mapSubscriptionDtoToSubscription(subscriptionDto);
        subscription = this.save(subscription);

        return this.subscriptionMapper.mapSubscriptionToSubscriptionDto(subscription);
    }

    private boolean isFirstSubscription(Subscription subscription) {
        return this.subscriptionDao.countByBorrowerId(subscription.getBorrower().getId()) < 2;
    }
}
