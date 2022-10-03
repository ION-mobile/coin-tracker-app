package de.ion.coinTrackerApp.error.singleton;

public class StringRequestErrorSingleton implements ErrorSingleton {
    private static StringRequestErrorSingleton stringRequestErrorSingleton;

    private String errorMessage = "";

    private StringRequestErrorSingleton() {}

    /**
     * @return stringRequestErrorSingleton
     */
    public static StringRequestErrorSingleton getInstance() {
        if (stringRequestErrorSingleton == null)
            stringRequestErrorSingleton = new StringRequestErrorSingleton();

        return stringRequestErrorSingleton;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
