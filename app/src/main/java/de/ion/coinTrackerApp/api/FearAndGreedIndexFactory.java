package de.ion.coinTrackerApp.api;

import de.ion.coinTrackerApp.api.valueObject.FearAndGreedIndex;

public interface FearAndGreedIndexFactory {
    /**
     * @param key
     * @return fearAndGreedIndex
     */
    public FearAndGreedIndex get(String key);
}
