package de.ion.coinTrackerApp.help;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import de.ion.coinTrackerApp.help.valueObject.HelpItem;

public class HelpItemForHelpActivityFactoryTest {
    private HelpItemFactory helpItemFactory;

    @Before
    public void setUp() {
        this.helpItemFactory = new HelpItemForHelpActivityFactory();
    }

    @Test
    public void testGetHelpItems_HappyPath() {
        HashMap<Integer, HelpItem> helpItems = this.helpItemFactory.getHelpItems();

        HashMap<Integer, HelpItem> expectedHelpItems = new HashMap<>();
        expectedHelpItems.put(0, new HelpItem("Wie funktioniert der Crypto Tracker?", "Du musst im Eingabefeld dein gewünschten Bitcoin Preis eingeben und der Tracker gibt dir sofort eine Warnung aus, wenn diese Position erreicht wurde. Der Tracker läuft im Hintergrund weiter."));
        expectedHelpItems.put(1, new HelpItem("Kann ich die Musik auch ändern?", "Nein die Musik ist statisch festgelegt, aber du kannst die Musik stumm schalten."));

        assertSame(expectedHelpItems.get(0).getQuestion(), helpItems.get(0).getQuestion());
        assertSame(expectedHelpItems.get(0).getAnswer(), helpItems.get(0).getAnswer());
        assertSame(expectedHelpItems.get(1).getQuestion(), helpItems.get(1).getQuestion());
        assertSame(expectedHelpItems.get(1).getAnswer(), helpItems.get(1).getAnswer());
    }
}
