package de.ion.coinTrackerApp.api.v1.crypto;

import android.annotation.SuppressLint;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import de.ion.coinTrackerApp.bitcoin.singleton.BitcoinSingleton;
import de.ion.coinTrackerApp.bitcoin.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.bitcoin.valueObject.CryptoData;
import de.ion.coinTrackerApp.error.singleton.ErrorSingleton;
import de.ion.coinTrackerApp.error.singleton.StringRequestErrorSingleton;
import de.ion.coinTrackerApp.api.CryptoPriceByCryptoCompareFactory;
import de.ion.coinTrackerApp.api.CryptoPriceFactory;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.api.singleton.CryptoApiSingleton;
import de.ion.coinTrackerApp.api.valueObject.CryptoPrice;

public class CryptoPriceByCryptoCompareStringRequest implements CryptoPriceStringRequest {
    private static final String JSON_BITCOIN_URL = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD";
    private final String ERROR_MESSAGE_NETWORK = "ERROR: Du kannst dich nicht mit dem Internet verbinden. Überprüfe deine Internetverbindung und versuche es später erneut!";
    private final String ERROR_MESSAGE_SERVER = "ERROR: Der Server konnte nicht gefunden werden. Versuche es später nochmal!";
    private final String ERROR_MESSAGE_PARSER = "ERROR: Parsing hat Probleme verursacht. Versuche es später nochmal!";

    private final CryptoSingleton cryptoSingleton;
    private CryptoPriceFactory cryptoPriceFactory;

    private final ApiSingleton cryptoApiSingleton;

    private final ErrorSingleton errorSingleton;

    public CryptoPriceByCryptoCompareStringRequest() {
        this.cryptoSingleton = BitcoinSingleton.getInstance();
        this.cryptoApiSingleton = CryptoApiSingleton.getInstance();
        this.errorSingleton = StringRequestErrorSingleton.getInstance();
    }

    @Override
    public void prepare() {
        final String[] errorMessage = {null};

        StringRequest stringBitcoinRequest = new StringRequest(Request.Method.GET, JSON_BITCOIN_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        cryptoPriceFactory = new CryptoPriceByCryptoCompareFactory(response);
                        CryptoPrice currentPrice = cryptoPriceFactory.get("USD");

                        if (cryptoSingleton.getBitcoinData() == null) {
                            CryptoData cryptoData = new CryptoData("bitcoin", Integer.toString(currentPrice.getCryptoPrice()), cryptoSingleton.getBitcoinData().getFearAndGreedIndex());
                            cryptoSingleton.setBitcoinData(cryptoData);
                        }

                        if (cryptoSingleton.getBitcoinData() != null) {
                            CryptoData cryptoData = cryptoSingleton.getBitcoinData();
                            cryptoData = new CryptoData("bitcoin", Integer.toString(currentPrice.getCryptoPrice()), cryptoSingleton.getBitcoinData().getFearAndGreedIndex());
                            cryptoSingleton.setBitcoinData(cryptoData);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof TimeoutError) {
                    errorMessage[0] = ERROR_MESSAGE_NETWORK;
                } else if (error instanceof ServerError) {
                    errorMessage[0] = ERROR_MESSAGE_SERVER;
                } else if (error instanceof ParseError) {
                    errorMessage[0] = ERROR_MESSAGE_PARSER;
                }
                errorSingleton.setErrorMessage(errorMessage[0]);
            }
        });

        this.cryptoApiSingleton.setStringRequest(stringBitcoinRequest);
    }
}
