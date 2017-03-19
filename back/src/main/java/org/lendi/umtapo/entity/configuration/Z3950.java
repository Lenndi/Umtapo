package org.lendi.umtapo.entity.configuration;

import java.util.Map;

/**
 * Z39.50 entity.
 */
public class Z3950 {
    private int id;
    private String name;
    private String url;
    private int port;
    private String syntax;
    /**
     * Time to live connection in ms.
     */
    private long ttl;
    private Map<String, String> database;
    private Map<String, String> options;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets port.
     *
     * @param port the port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets syntax.
     *
     * @return the syntax
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Sets syntax.
     *
     * @param syntax the syntax
     */
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public long getTtl() {
        return ttl;
    }

    /**
     * Sets ttl.
     *
     * @param ttl the ttl
     */
    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public Map<String, String> getDatabase() {
        return database;
    }

    /**
     * Sets database.
     *
     * @param database the database
     */
    public void setDatabase(Map<String, String> database) {
        this.database = database;
    }

    /**
     * Gets options.
     *
     * @return the options
     */
    public Map<String, String> getOptions() {
        return options;
    }

    /**
     * Sets options.
     *
     * @param options the options
     */
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}
