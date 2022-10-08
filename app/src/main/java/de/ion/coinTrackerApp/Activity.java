package de.ion.coinTrackerApp;

public interface Activity {
    public void loadViews();

    public void initComponents();

    public void initDatabase();

    public void initSingleton();

    public void initToolbar();

    public void init();
}
