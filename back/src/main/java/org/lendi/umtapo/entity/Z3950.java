package org.lendi.umtapo.entity;

import java.util.Map;

/**
 * Z39.50 entity.
 */
public class Z3950 {
    private String name;
    private String url;
    private int port;
    private String syntax;
    private Map<String, String> database;
    private Map<String, String>[] options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public Map<String, String> getDatabase() {
        return database;
    }

    public void setDatabase(Map<String, String> database) {
        this.database = database;
    }

    public Map<String, String>[] getOptions() {
        return options;
    }

    public void setOptions(Map<String, String>[] options) {
        this.options = options;
    }
}
