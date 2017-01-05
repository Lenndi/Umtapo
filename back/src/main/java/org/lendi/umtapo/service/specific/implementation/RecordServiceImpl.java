package org.lendi.umtapo.service.specific.implementation;

import org.apache.log4j.Logger;
import org.lendi.umtapo.entity.configuration.Z3950;
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
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Math.toIntExact;

/**
 * RecordService implementation.
 */
@Service
public class RecordServiceImpl implements RecordService {
    private final static Logger logger = Logger.getLogger(RecordServiceImpl.class);

    private Z3950 defaultLibrary;
    private Map<Integer, Z3950> libraries;
    private Connection connection;

    @Autowired
    public RecordServiceImpl(Z3950Service z3950Service) {
        Assert.notNull(z3950Service, "Argument z3950Service cannot be null.");
        this.libraries = z3950Service.findAll();
    }

    @Override
    public Record findByISBN(String isbn) throws ZoomException {
        this.createConnection();
        Record record = null;

        String isbnAttr = this.defaultLibrary.getAttributes().get("isbn");
        Query query = new PrefixQuery("@attr 1=" + isbnAttr + " " + isbn);
        ResultSet set = this.connection.search(query);

        if (set.getHitCount() > 0) {
            InputStream input = new ByteArrayInputStream(set.getRecord(0).getContent());
            MarcReader reader = new MarcStreamReader(input);
            record = reader.next();
        }

        return record;
    }

    @Override
    public List<Record> findByTitle(String title, Integer start, Integer end) throws ZoomException {
        this.createConnection();

        String titleAttr = this.defaultLibrary.getAttributes().get("title");
        Query query = new PrefixQuery("@attr 1=" + titleAttr + " \"" + title + "\"");
        ResultSet set = this.connection.search(query);
        logger.info("Search hits " + set.getHitCount() + " records with title " + title);

        return this.getRecordsFromSet(set, start, end);
    }

    @Override
    public List<Record> findByTitle(String title) throws ZoomException {
        return this.findByTitle(title, 0, -1);
    }

    /**
     * Define the default library based on Z39.50 configuration number.
     *
     * @param libraryNumber Z39.50 id in z39-50.yml file
     */
    public void setDefaultLibrary(Integer libraryNumber) {
        this.defaultLibrary = this.libraries.get(libraryNumber);

        if (this.connection != null && !this.connection.isClose()) {
            this.connection.close();
        }
    }

    /**
     * Create Z39.50 connection. If connection does not exist or is close, it creates a new one.
     */
    private void createConnection() throws ZoomException {
        if (this.defaultLibrary == null) {
            this.setDefaultLibrary(1);
        }

        if (this.connection == null || this.connection.isClose()) {
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

    private List<Record> getRecordsFromSet(ResultSet set, Integer start, Integer end) throws ZoomException {
        if (end == -1 && set.getHitCount() < 50) {
            if (set.getHitCount() < 50) {
                end = toIntExact(set.getHitCount());
            } else {
                end = 50;
            }
        }
        if (start > set.getHitCount()) {
            return new ArrayList<>();
        } else if (end > set.getHitCount()) {
            end = toIntExact(set.getHitCount());
        }

        List<Record> marcRecords = new ArrayList<>();
        List<org.yaz4j.Record> yazRecords = set.getRecords(start.longValue(), end);

        yazRecords.forEach(record -> {
            InputStream input = new ByteArrayInputStream(record.getContent());
            MarcReader reader = new MarcStreamReader(input, "UTF-8");
            marcRecords.add(reader.next());
        });

        return marcRecords;
    }
}
