package org.lendi.umtapo.service.specific.implementation;

import org.apache.log4j.Logger;
import org.lendi.umtapo.entity.configuration.Z3950;
import org.lendi.umtapo.entity.record.RecordListWrapper;
import org.lendi.umtapo.marc.Connection;
import org.lendi.umtapo.service.configuration.Z3950Service;
import org.lendi.umtapo.service.specific.RecordService;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final static Logger logger = Logger.getLogger(RecordServiceImpl.class);

    private Z3950 defaultLibrary;
    private Map<Integer, Z3950> libraries;
    private Connection connection;
    private long connectionStartTime;

    @Autowired
    public RecordServiceImpl(Z3950Service z3950Service) {
        Assert.notNull(z3950Service, "Argument z3950Service cannot be null.");
        this.libraries = z3950Service.findAll();
    }

    @Override
    public Record findByISBN(String isbn) throws ZoomException {
        this.createConnection();
        Record record = null;

        Query query = new PrefixQuery("@attr 1=7 " + isbn);
        ResultSet set = this.connection.search(query);

        if (set.getHitCount() > 0) {
            InputStream input = new ByteArrayInputStream(set.getRecord(0).getContent());
            MarcReader reader = new MarcStreamReader(input, "UTF-8");
            record = reader.next();
        }

        return record;
    }

    @Override
    public RecordListWrapper<Record> findByTitle(String title, Integer start, Integer count) throws ZoomException {
        this.createConnection();
        RecordListWrapper<Record> recordListWrapper = new RecordListWrapper<>();

        Query query = new PrefixQuery("@attr 1=4 \"" + title + "\"");
        ResultSet set;
        try {
            set = this.connection.search(query);
        } catch (ZoomException e) {
            this.connection.close();
            this.createConnection();
            set = this.connection.search(query);
        }
        logger.info("Search hits " + set.getHitCount() + " records with title " + title);

        recordListWrapper.setRecord(this.getRecordsFromSet(set, start, count));
        recordListWrapper.setHitCount((int) set.getHitCount());

        return recordListWrapper;
    }

    @Override
    public RecordListWrapper<Record> findByTitle(String title) throws ZoomException {
        return this.findByTitle(title, 0, -1);
    }

    @Override
    public void setDefaultLibrary(int libraryNumber) {
        if (this.defaultLibrary == null || this.defaultLibrary.getId() != libraryNumber) {
            this.defaultLibrary = this.libraries.get(libraryNumber);
            if (this.connection != null && !this.connection.isClose()) {
                this.connection.close();
            }
        }
    }

    /**
     * Create Z39.50 connection. If connection does not exist or is close, it creates a new one.
     */
    private void createConnection() throws ZoomException {
        if (this.defaultLibrary == null) {
            this.setDefaultLibrary(1);
        }

        if (!this.isConnectionAlive()) {
            if (this.connection != null && !this.connection.isClose()) {
                this.connection.close();
            }
            this.connectionStartTime = System.currentTimeMillis();
            Connection connection = new Connection(this.defaultLibrary.getUrl(), this.defaultLibrary.getPort());
            connection.setDatabaseName(this.defaultLibrary.getDatabase().get("name"));
            connection.setUsername(this.defaultLibrary.getDatabase().get("username"));
            connection.setPassword(this.defaultLibrary.getDatabase().get("password"));
            connection.setSyntax(this.defaultLibrary.getSyntax());
            this.defaultLibrary.getOptions().forEach(connection::option);

            this.connection = connection;
            this.connection.connect();
        }
    }

    private List<Record> getRecordsFromSet(ResultSet set, Integer start, Integer count) throws ZoomException {
        logger.info("getRecordsFromSet: entering, start=" + start + ", count=" + count);
        if (count == -1 && set.getHitCount() < 50) {
            if (set.getHitCount() < 50) {
                count = (int) set.getHitCount();
            } else {
                count = 50;
            }
        }
        if (start > set.getHitCount()) {
            return new ArrayList<>();
        } else if ((start + count) > set.getHitCount()) {
            count = (int) set.getHitCount() - start;
        }
        logger.info("getRecordsFromSet: after treatment, start=" + start + ", count=" + count);

        List<Record> marcRecords = new ArrayList<>();
        List<org.yaz4j.Record> yazRecords = set.getRecords(start.longValue(), count);

        yazRecords.forEach(record -> {
            InputStream input = new ByteArrayInputStream(record.getContent());
            MarcReader reader = new MarcStreamReader(input, "UTF-8");
            marcRecords.add(reader.next());
        });

        return marcRecords;
    }

    private boolean isConnectionAlive() {
        boolean isOverTtl = (System.currentTimeMillis() - this.connectionStartTime) > this.defaultLibrary.getTtl();

        return ((this.connection != null) && !this.connection.isClose() && !isOverTtl);
    }
}
