package org.lendi.umtapo.marc;

/**
 * Umtapo yaz4j Connection extension.
 */
public class Connection extends org.yaz4j.Connection {

    public Connection(String host, int port) {
        super(host, port);
    }

    public boolean isClose() {
        return this.closed;
    }
}
