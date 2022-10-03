package de.ion.coinTrackerApp.error.singleton;

public interface ErrorSingleton {
    /**
     * @return errorMessage
     */
    String getErrorMessage();

    /**
     * @param errorMessage
     */
    void setErrorMessage(String errorMessage);
}
