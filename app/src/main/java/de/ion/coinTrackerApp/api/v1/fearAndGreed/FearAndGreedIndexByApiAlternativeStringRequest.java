package de.ion.coinTrackerApp.api.v1.fearAndGreed;

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
import de.ion.coinTrackerApp.api.FearAndGreedIndexByApiAlternativeFactory;
import de.ion.coinTrackerApp.api.FearAndGreedIndexFactory;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.api.singleton.FearAndGreedApiSingleton;
import de.ion.coinTrackerApp.api.valueObject.FearAndGreedIndex;

public class FearAndGreedIndexByApiAlternativeStringRequest implements FearAndGreedIndexStringRequest {
    private static final String FEAR_AND_GREED_API_URL = "https://api.alternative.me/fng/?limit=2";
    private final String ERROR_MESSAGE_NETWORK = "ERROR: Du kannst dich nicht mit dem Internet verbinden. Überprüfe deine Internetverbindung und versuche es später erneut!";
    private final String ERROR_MESSAGE_SERVER = "ERROR: Der Server konnte nicht gefunden werden. Versuche es später nochmal!";
    private final String ERROR_MESSAGE_PARSER = "ERROR: Parsing hat Probleme verursacht. Versuche es später nochmal!";

    private FearAndGreedIndexFactory fearAndGreedIndexFactory;

    private final CryptoSingleton cryptoSingleton;
    private final ApiSingleton fearAndGreedApiSingleton;
    private final ErrorSingleton errorSingleton;

    public FearAndGreedIndexByApiAlternativeStringRequest() {
        this.cryptoSingleton = BitcoinSingleton.getInstance();
        this.fearAndGreedApiSingleton = FearAndGreedApiSingleton.getInstance();
        this.errorSingleton = StringRequestErrorSingleton.getInstance();
    }

    @Override
    public void prepare() {
        final String[] errorMessage = {null};

        StringRequest stringFearAndGreedRequest = new StringRequest(Request.Method.GET, FEAR_AND_GREED_API_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        fearAndGreedIndexFactory = new FearAndGreedIndexByApiAlternativeFactory(response);
                        FearAndGreedIndex fearAndGreedIndex = fearAndGreedIndexFactory.get("value");

                        if (cryptoSingleton.getBitcoinData() == null) {
                            CryptoData cryptoData = new CryptoData("", "", fearAndGreedIndex.getFearAndGreedIndex());
                            cryptoSingleton.setBitcoinData(cryptoData);
                        }

                        if (cryptoSingleton.getBitcoinData() != null) {
                            CryptoData cryptoData = cryptoSingleton.getBitcoinData();
                            cryptoData = new CryptoData(cryptoSingleton.getBitcoinData().getCryptoName(), cryptoSingleton.getBitcoinData().getCurrentCryptoPrice(), fearAndGreedIndex.getFearAndGreedIndex());
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

        this.fearAndGreedApiSingleton.setStringRequest(stringFearAndGreedRequest);
    }
}
