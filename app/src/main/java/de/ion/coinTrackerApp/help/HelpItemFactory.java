package de.ion.coinTrackerApp.help;

import java.util.HashMap;

import de.ion.coinTrackerApp.help.valueObject.HelpItem;

public interface HelpItemFactory {
    /**
     * @return helpItems
     */
    public HashMap<Integer, HelpItem> getHelpItems();
}
