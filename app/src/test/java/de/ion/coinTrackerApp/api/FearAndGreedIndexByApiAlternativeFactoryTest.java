package de.ion.coinTrackerApp.api;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import de.ion.coinTrackerApp.api.valueObject.FearAndGreedIndex;

public class FearAndGreedIndexByApiAlternativeFactoryTest {
    @Test
    public void testGetFearAndGreedIndex_HappyPath() {
        FearAndGreedIndexFactory fearAndGreedIndexFactory = new FearAndGreedIndexByApiAlternativeFactory("{\n" +
                "  \"name\": \"test\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"value\": \"20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"value\": \"22\"\n" +
                "    }\n" +
                "  ]\n" +
                "}");

        FearAndGreedIndex fearAndGreedIndex = fearAndGreedIndexFactory.get("value");
        assertSame(20, fearAndGreedIndex.getFearAndGreedIndex());
    }
}
