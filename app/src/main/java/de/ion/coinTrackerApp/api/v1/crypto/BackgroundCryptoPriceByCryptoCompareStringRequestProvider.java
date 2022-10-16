package de.ion.coinTrackerApp.api.v1.crypto;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import de.ion.coinTrackerApp.api.CryptoPriceByCryptoCompareFactory;
import de.ion.coinTrackerApp.api.CryptoPriceFactory;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.api.singleton.CryptoApiSingleton;
import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;
import de.ion.coinTrackerApp.database.DatabaseCryptoRepository;
import de.ion.coinTrackerApp.database.SQLiteCryptoRepository;
import de.ion.coinTrackerApp.error.singleton.ErrorSingleton;
import de.ion.coinTrackerApp.error.singleton.StringRequestErrorSingleton;
import de.ion.coinTrackerApp.service.background.valueObjects.CurrentPrice;

public class BackgroundCryptoPriceByCryptoCompareStringRequestProvider implements CryptoPriceStringRequestProvider {
    private final Context context;

    private static final String CRYPTO_NAME = "bitcoin";
    private static final String CRYPTO_API_URL = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD";
    private static final String ERROR_MESSAGE_NETWORK = "ERROR: Du kannst dich nicht mit dem Internet verbinden. Überprüfe deine Internetverbindung und versuche es später erneut!";
    private static final String ERROR_MESSAGE_SERVER = "ERROR: Ein Fehler ist aufgetreten. Versuche es später nochmal!";

    private CryptoPriceFactory cryptoPriceFactory;

    private final DatabaseCryptoRepository databaseCryptoRepository;

    public BackgroundCryptoPriceByCryptoCompareStringRequestProvider(Context context) {
        this.context = context;
        this.databaseCryptoRepository = new SQLiteCryptoRepository(context);
    }

    @Override
    public void prepareCryptoPriceRequest() {
        final String[] errorMessage = {null};

        StringRequest stringCryptoRequest = new StringRequest(Request.Method.GET, CRYPTO_API_URL,
                response -> {
                    cryptoPriceFactory = new CryptoPriceByCryptoCompareFactory(response);
                    CryptoPrice cryptoPrice = cryptoPriceFactory.get("USD");

                    CurrentPrice currentPrice = new CurrentPrice(cryptoPrice.getCryptoPrice());

                    this.databaseCryptoRepository.persist(
                            new CryptoData(
                                    CRYPTO_NAME,
                                    currentPrice.getCryptoPrice(),
                                    0));
                }, error -> {
            if (error instanceof NetworkError || error instanceof AuthFailureError ||
                    error instanceof TimeoutError) {
                errorMessage[0] = ERROR_MESSAGE_NETWORK;
            } else if (error instanceof ServerError) {
                errorMessage[0] = ERROR_MESSAGE_SERVER;
            } else if (error instanceof ParseError) {
                errorMessage[0] = ERROR_MESSAGE_SERVER;
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(stringCryptoRequest);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
