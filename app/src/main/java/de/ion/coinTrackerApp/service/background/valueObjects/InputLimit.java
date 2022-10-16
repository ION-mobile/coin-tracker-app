package de.ion.coinTrackerApp.service.background.valueObjects;

public class InputLimit {
    private final Integer limit;

    /**
     * @param limit
     */
    public InputLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return limit
     */
    public Integer getCryptoLimit() {
        return this.limit;
    }
}
