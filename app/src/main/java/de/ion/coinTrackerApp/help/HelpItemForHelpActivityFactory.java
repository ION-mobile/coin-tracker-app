package de.ion.coinTrackerApp.help;

import java.util.HashMap;

import de.ion.coinTrackerApp.help.valueObject.HelpItem;

public class HelpItemForHelpActivityFactory implements HelpItemFactory{
    /**
     * @return helpItems
     */
    public HashMap<Integer, HelpItem> getHelpItems() {
        HashMap<Integer, HelpItem> helpItems = new HashMap<>();
        helpItems.put(0, new HelpItem("Wie funktioniert der Bitcoin Tracker?", "Du musst im Eingabefeld dein gewünschten Bitcoin Preis eingeben und der Tracker gibt dir sofort eine Warnung aus, wenn diese Position erreicht wurde. Der Tracker läuft im Hintergrund weiter. ACHTUNG!!! Wenn du dein Smartphone ausschaltest wird dein Stand gelöscht."));
        helpItems.put(1, new HelpItem("Kann ich die Musik auch ändern?", "Nein die Musik ist statisch festgelegt, aber du kannst die Musik stumm schalten."));

        return helpItems;
    }
}
