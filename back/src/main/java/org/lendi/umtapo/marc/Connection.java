package org.lendi.umtapo.marc;

/**
 * Umtapo yaz4j Connection extension.
 */
public class Connection extends org.yaz4j.Connection {

    /**
     * Instantiates a new Connection.
     *
     * @param host the host
     * @param port the port
     */
    public Connection(String host, int port) {
        super(host, port);
    }

    /**
     * Is close boolean.
     *
     * @return the boolean
     */
    public boolean isClose() {
        return this.closed;
    }
}
