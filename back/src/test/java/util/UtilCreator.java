package util;

import org.lendi.umtapo.dto.AddressDto;
import org.lendi.umtapo.dto.BorrowerDto;
import org.lendi.umtapo.dto.ItemDto;
import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.entity.Address;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.Item;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.entity.ShelfMark;
import org.lendi.umtapo.enumeration.Condition;
import org.lendi.umtapo.enumeration.ItemType;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class UtilCreator {

    private ZonedDateTime birthday;
    private ZonedDateTime olderReturn;
    private ZonedDateTime subscriptionStart;
    private ZonedDateTime subscriptionEnd;
    private ZonedDateTime loanStart;
    private ZonedDateTime loanEnd;

    public UtilCreator() {
        birthday = java.time.ZonedDateTime.of(1984, 7, 14, 0, 0, 0, 0, ZoneId.systemDefault());
        olderReturn = java.time.ZonedDateTime.of(2017, 1, 14, 0, 0, 0, 0, ZoneId.systemDefault());
        subscriptionStart = java.time.ZonedDateTime.of(2016, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        subscriptionEnd = java.time.ZonedDateTime.of(2017, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        loanStart = java.time.ZonedDateTime.of(2017, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        loanEnd = java.time.ZonedDateTime.of(2017, 2, 1, 0, 0, 0, 0, ZoneId.systemDefault());
    }

    public BorrowerDocument createBorrowerDocument(String id, String name, String email) {
        BorrowerDocument borrowerDocument = new BorrowerDocument();
        borrowerDocument.setId(id);
        borrowerDocument.setName(name);
        borrowerDocument.setComment("Un bien bel usager.");
        borrowerDocument.setBirthday(birthday);
        borrowerDocument.setQuota(5);
        borrowerDocument.setEmailOptin(true);
        borrowerDocument.setAddressId("3");
        borrowerDocument.setAddress1("3 rue des poules");
        borrowerDocument.setAddress2("Étage 5");
        borrowerDocument.setZip("35000");
        borrowerDocument.setCity("Rennes");
        borrowerDocument.setPhone("0299813994");
        borrowerDocument.setEmail(email);
        borrowerDocument.setNbLoans(3);
        borrowerDocument.setTooMuchLoans(false);
        borrowerDocument.setOlderReturn(olderReturn);
        borrowerDocument.setSubscriptionStart(subscriptionStart);
        borrowerDocument.setSubscriptionEnd(subscriptionEnd);

        return borrowerDocument;
    }

    public Borrower createBorrower(Integer id) {
        Address address = new Address();
        Borrower borrower = new Borrower();

        borrower.setId(id);
        borrower.setName("Michel Test");
        borrower.setComment("Un bien bel usager.");
        borrower.setBirthday(birthday);
        borrower.setQuota(5);
        borrower.setEmailOptin(true);
        address.setId(3);
        address.setAddress1("3 rue des poules");
        address.setAddress2("Étage 5");
        address.setZip("35000");
        address.setCity("Rennes");
        address.setPhone("0299813994");
        address.setEmail("michel@test.com");
        borrower.setAddress(address);

        return borrower;
    }

    public BorrowerDto createBorrowerDto(Integer id) {
        AddressDto address = new AddressDto();
        BorrowerDto borrower = new BorrowerDto();

        borrower.setId(id);
        borrower.setName("Michel Test");
        borrower.setComment("Un bien bel usager.");
        borrower.setBirthday(birthday);
        borrower.setQuota(5);
        borrower.setEmailOptin(true);
        address.setId(3);
        address.setAddress1("3 rue des poules");
        address.setAddress2("Étage 5");
        address.setZip("35000");
        address.setCity("Rennes");
        address.setPhone("0299813994");
        address.setEmail("michel@test.com");
        borrower.setAddress(address);

        return borrower;
    }

    public Library createLibrary(Integer id) {
        Library library = new Library();

        library.setId(id);
        library.setName("Test Library");
        library.setBorrowDuration(30);
        library.setSubscriptionDuration(365);
        library.setCurrency("€");
        library.setDefaultZ3950(1);
        library.setShelfMarkNb(3);
        library.setUseDeweyClassification(false);

        return library;
    }

    public Item createItem(Integer id, Integer internalId) {
        Item item = new Item();
        Library library = new Library();
        library.setId(1);

        item.setId(id);
        item.setInternalId(internalId);
        item.setCurrency("€");
        item.setBorrowed(false);
        item.setCondition(Condition.NEW);
        item.setLibrary(library);
        item.setLoanable(true);
        item.setPurchasePrice(600);
        item.setType(ItemType.BOOK);
        item.setShelfmark(new ShelfMark("TES", "002", "URI"));

        return item;
    }

    public ItemDto createItemDto(Integer id, Integer internalId) {
        ItemDto item = new ItemDto();
        LibraryDto library = new LibraryDto();
        library.setId(1);

        item.setId(id);
        item.setInternalId(internalId);
        item.setCurrency("€");
        item.setBorrowed(false);
        item.setCondition(Condition.NEW);
        item.setLibrary(library);
        item.setLoanable(true);
        item.setPurchasePrice(6.0f);
        item.setType(ItemType.BOOK);
        item.setShelfmark(new ShelfMark("TES", "002", "URI"));

        return item;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public ZonedDateTime getOlderReturn() {
        return olderReturn;
    }

    public ZonedDateTime getSubscriptionStart() {
        return subscriptionStart;
    }

    public ZonedDateTime getSubscriptionEnd() {
        return subscriptionEnd;
    }

    public ZonedDateTime getLoanStart() {
        return loanStart;
    }

    public ZonedDateTime getLoanEnd() {
        return loanEnd;
    }
}
