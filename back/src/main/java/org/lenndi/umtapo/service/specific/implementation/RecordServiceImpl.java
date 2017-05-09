package org.lenndi.umtapo.service.specific.implementation;

import com.github.ladutsko.isbn.ISBN;
import com.github.ladutsko.isbn.ISBNException;
import org.apache.log4j.Logger;
import org.lenndi.umtapo.entity.configuration.Z3950;
import org.lenndi.umtapo.marc.Connection;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.RecordService;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.yaz4j.PrefixQuery;
import org.yaz4j.Query;
import org.yaz4j.ResultSet;
import org.yaz4j.exception.ZoomException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RecordService implementation.
 */
@Service
public class RecordServiceImpl implements RecordService {
    private static final Logger LOGGER = Logger.getLogger(RecordServiceImpl.class);

    private static final int EAN_SIZE = 13;
    public static final int ISBN13_SIZE = 13;
    public static final int ISBN10_SIZE = 10;

    private Z3950 library;
    private Map<Integer, Z3950> libraries;
    private Connection connection;
    private long connectionStartTime;

    /**
     * Instantiates a new Record service.
     *
     * @param z3950Service the z 3950 service
     */
    @Autowired
    public RecordServiceImpl(Z3950Service z3950Service) {
        Assert.notNull(z3950Service, "Argument z3950Service cannot be null.");
        this.libraries = z3950Service.findAll();
    }

    @Override
    public Record findByRawISBN(String rawIsbn) throws ZoomException, ISBNException {
        Record record;

        if (ISBN.isValid(rawIsbn)) {
            if (ISBN.isIsbn13(rawIsbn)) {
                record = this.findByEAN(ISBN.normalize(rawIsbn));

                if (record == null) {
                    ISBN isbn = ISBN.parseIsbn(rawIsbn);
                    record = this.findByISBN(isbn.getIsbn10());
                }
            } else {
                record = this.findByISBN(ISBN.normalize(rawIsbn));
            }
        } else {
            throw new ISBNException(rawIsbn + " is not a valid ISBN number.");
        }

        return record;
    }

    @Override
    public Record findByISBN(String isbn) throws ZoomException {
        this.createConnection();

        Query query = new PrefixQuery("@attr 1=7 " + isbn);
        ResultSet set = this.safeSearch(query);

        return this.getFirstRecordFromSet(set);
    }

    @Override
    public Record findByEAN(String ean) throws ZoomException {
        this.createConnection();

        Query query = new PrefixQuery("@attr 1=1214 " + ean);
        ResultSet set = this.safeSearch(query);

       return this.getFirstRecordFromSet(set);
    }

    @Override
    public Page<Record> findByTitle(String title, Pageable pageable) throws ZoomException {
        this.createConnection();
        int start = pageable.getPageSize() * pageable.getPageNumber();
        int count = pageable.getPageSize();

        Query query = new PrefixQuery("@attr 1=4 \"" + title + "\" @attr 2=3");
        ResultSet set = this.safeSearch(query);
        LOGGER.info("Search hits " + set.getHitCount() + " records with title " + title);

        List<Record> records = this.getRecordsFromSet(set, start, count);

        return new PageImpl<>(records, pageable, set.getHitCount());
    }

    @Override
    public Page<Record> findByTitle(String title) throws ZoomException {
        return this.findByTitle(title, new PageRequest(0, -1));
    }

    @Override
    public void setLibrary(int libraryNumber) {
        if (this.library == null || this.library.getId() != libraryNumber) {
            this.library = this.libraries.get(libraryNumber);
            if (this.connection != null && !this.connection.isClose()) {
                this.connection.close();
            }
        }
    }

    /**
     * Create Z39.50 connection. If connection does not exist or is close, it creates a new one.
     */
    private void createConnection() throws ZoomException {
        if (this.library == null) {
            this.setLibrary(1);
        }

        if (!this.isConnectionAlive()) {
            if (this.connection != null && !this.connection.isClose()) {
                this.connection.close();
            }
            this.connectionStartTime = System.currentTimeMillis();
            this.connection = new Connection(this.library.getUrl(), this.library.getPort());
            this.connection.setDatabaseName(this.library.getDatabase().get("name"));
            this.connection.setUsername(this.library.getDatabase().get("username"));
            this.connection.setPassword(this.library.getDatabase().get("password"));
            this.connection.setSyntax(this.library.getSyntax());
            this.library.getOptions().forEach(connection::option);

            this.connection.connect();
        }
    }

    private List<Record> getRecordsFromSet(ResultSet set, Integer start, Integer count) throws ZoomException {
        LOGGER.info("getRecordsFromSet: entering, start=" + start + ", count=" + count);

        final int maxCount = 50;
        if (count == -1 && set.getHitCount() < maxCount) {
            if (set.getHitCount() < maxCount) {
                count = (int) set.getHitCount();
            } else {
                count = maxCount;
            }
        }
        if (start > set.getHitCount()) {
            return new ArrayList<>();
        } else if ((start + count) > set.getHitCount()) {
            count = (int) set.getHitCount() - start;
        }
        LOGGER.info("getRecordsFromSet: after treatment, start=" + start + ", count=" + count);

        List<Record> marcRecords = new ArrayList<>();
        List<org.yaz4j.Record> yazRecords = set.getRecords(start.longValue(), count);

        yazRecords.forEach(record -> {
            InputStream input = new ByteArrayInputStream(record.getContent());
            MarcReader reader = new MarcStreamReader(input, "UTF-8");
            marcRecords.add(reader.next());
        });

        return marcRecords;
    }

    private Record getFirstRecordFromSet(ResultSet set) throws ZoomException {
        if (set.getHitCount() > 0) {
            InputStream input = new ByteArrayInputStream(set.getRecord(0).getContent());
            MarcReader reader = new MarcStreamReader(input, "UTF-8");
            return reader.next();
        } else {
            return null;
        }
    }

    private boolean isConnectionAlive() {
        boolean isOverTtl = (System.currentTimeMillis() - this.connectionStartTime) > this.library.getTtl();

        return ((this.connection != null) && !this.connection.isClose() && !isOverTtl);
    }

    private ResultSet safeSearch(Query query) throws ZoomException {
        try {
            return this.connection.search(query);
        } catch (final ZoomException e) {
            this.connection.close();
            this.createConnection();
            return this.connection.search(query);
        }
    }
}
