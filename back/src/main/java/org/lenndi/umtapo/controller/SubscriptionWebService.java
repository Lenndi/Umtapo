package org.lenndi.umtapo.controller;

import org.apache.log4j.Logger;
import org.lenndi.umtapo.dto.SubscriptionDto;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.exception.BadSubscriptionDateException;
import org.lenndi.umtapo.service.specific.LibraryService;
import org.lenndi.umtapo.service.specific.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;


/**
 * Subscription web service.
 */
@RestController
public class SubscriptionWebService {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionWebService.class);

    private final SubscriptionService subscriptionService;
    private final LibraryService libraryService;

    /**
     * Instantiates a new Loan web service.
     *
     * @param subscriptionService the subscription service
     * @param libraryService      the library service
     */
    public SubscriptionWebService(SubscriptionService subscriptionService, LibraryService libraryService) {
        this.subscriptionService = subscriptionService;
        this.libraryService = libraryService;
    }

    /**
     * Sets loan.
     *
     * @param subscriptionDto the subscription dto
     * @return the loan
     */
    @RequestMapping(value = "/subscriptions",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity setSubscription(@RequestBody SubscriptionDto subscriptionDto) {

        if (subscriptionDto.getBorrower() != null && subscriptionDto.getBorrower().getId() != null
                && subscriptionDto.getLibrary() != null && subscriptionDto.getLibrary().getId() != null
                && subscriptionDto.getContribution() != null) {

            if (subscriptionDto.getStart() == null) {
                subscriptionDto.setStart(ZonedDateTime.now());
            }
            if (subscriptionDto.getEnd() == null) {
                Library library = this.libraryService.findOne(subscriptionDto.getLibrary().getId());
                if (library != null) {
                    subscriptionDto.setEnd(subscriptionDto.getStart().plusDays(library.getSubscriptionDuration()));
                } else {
                    return new ResponseEntity<>("Library does not exists", HttpStatus.BAD_REQUEST);
                }
            }

            try {
                subscriptionDto = this.subscriptionService.saveDto(subscriptionDto);
            } catch (final BadSubscriptionDateException e) {
                return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
            }
            LOGGER.info("Subscription " + subscriptionDto.getId() + " inserted in database.");

            return new ResponseEntity<>(subscriptionDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
