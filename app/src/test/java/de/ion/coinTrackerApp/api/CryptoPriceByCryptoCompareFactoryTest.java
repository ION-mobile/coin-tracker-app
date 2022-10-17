package de.ion.coinTrackerApp.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;

public class CryptoPriceByCryptoCompareFactoryTest {

    @Test
    public void testGetCryptoPrice_HappyPath() {
        CryptoPriceFactory cryptoPriceFactory = new CryptoPriceByCryptoCompareFactory("{\"USD\": 20000}");

        CryptoPrice cryptoPrice = cryptoPriceFactory.get("USD");
        assertEquals(20000, cryptoPrice.getCryptoPrice(), 0);
    }
}
