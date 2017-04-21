package org.lenndi.umtapo.enumeration;

/**
 * The enum State.
 */
public enum State {

    /**
     * Active state.
     */
    ACTIVE("Active"),
    /**
     * Inactive state.
     */
    INACTIVE("Inactive"),
    /**
     * Deleted state.
     */
    DELETED("Deleted"),
    /**
     * Locked state.
     */
    LOCKED("Locked");

    private String state;

    State(final String state) {
        this.state = state;
    }

    /**
     * Get state string.
     *
     * @return the string
     */
    public String getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return this.state;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName() {
        return this.name();
    }


}
