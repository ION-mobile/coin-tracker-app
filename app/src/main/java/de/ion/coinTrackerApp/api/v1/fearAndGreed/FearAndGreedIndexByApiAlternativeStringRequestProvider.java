package de.ion.coinTrackerApp.api.v1.fearAndGreed;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;

import de.ion.coinTrackerApp.api.FearAndGreedIndexByApiAlternativeFactory;
import de.ion.coinTrackerApp.api.FearAndGreedIndexFactory;
import de.ion.coinTrackerApp.api.singleton.ApiSingleton;
import de.ion.coinTrackerApp.api.singleton.FearAndGreedApiSingleton;
import de.ion.coinTrackerApp.api.valueObject.FearAndGreedIndex;
import de.ion.coinTrackerApp.crypto.singleton.CryptoDataSingleton;
import de.ion.coinTrackerApp.crypto.singleton.CryptoSingleton;
import de.ion.coinTrackerApp.crypto.valueObject.CryptoData;
import de.ion.coinTrackerApp.error.singleton.ErrorSingleton;
import de.ion.coinTrackerApp.error.singleton.StringRequestErrorSingleton;

public class FearAndGreedIndexByApiAlternativeStringRequestProvider implements FearAndGreedIndexStringRequestProvider {
    private static final String FEAR_AND_GREED_API_URL = "https://api.alternative.me/fng/?limit=2";
    private static final String ERROR_MESSAGE_NETWORK = "ERROR: Du kannst dich nicht mit dem Internet verbinden. Überprüfe deine Internetverbindung und versuche es später erneut!";
    private static final String ERROR_MESSAGE_SERVER = "ERROR: Der Server konnte nicht gefunden werden. Versuche es später nochmal!";

    private FearAndGreedIndexFactory fearAndGreedIndexFactory;

    private final CryptoSingleton cryptoSingleton;
    private final ApiSingleton fearAndGreedApiSingleton;
    private final ErrorSingleton errorSingleton;

    public FearAndGreedIndexByApiAlternativeStringRequestProvider() {
        this.cryptoSingleton = CryptoDataSingleton.getInstance();
        this.fearAndGreedApiSingleton = FearAndGreedApiSingleton.getInstance();
        this.errorSingleton = StringRequestErrorSingleton.getInstance();
    }

    @Override
    public void prepareFearAndGreedRequest() {
        final String[] errorMessage = {null};

        StringRequest stringFearAndGreedRequest = new StringRequest(Request.Method.GET, FEAR_AND_GREED_API_URL,
                response -> {
                    fearAndGreedIndexFactory = new FearAndGreedIndexByApiAlternativeFactory(response);
                    FearAndGreedIndex fearAndGreedIndex = fearAndGreedIndexFactory.get("value");

                    if (cryptoSingleton.getCryptoData() == null) {
                        CryptoData cryptoData = new CryptoData(
                                "",
                                0,
                                fearAndGreedIndex.getFearAndGreedIndex());
                        cryptoSingleton.setCryptoData(cryptoData);
                    }

                    if (cryptoSingleton.getCryptoData() != null) {
                        CryptoData cryptoData = new CryptoData(
                                cryptoSingleton.getCryptoData().getCryptoName(),
                                cryptoSingleton.getCryptoData().getCurrentCryptoPrice(),
                                fearAndGreedIndex.getFearAndGreedIndex());
                        cryptoSingleton.setCryptoData(cryptoData);
                    }
                }, error -> {
                    if (error instanceof NetworkError || error instanceof AuthFailureError ||
                            error instanceof TimeoutError) {
                        errorMessage[0] = ERROR_MESSAGE_NETWORK;
                    } else if (error instanceof ServerError) {
                        errorMessage[0] = ERROR_MESSAGE_SERVER;
                    } else if (error instanceof ParseError) {
                        errorMessage[0] = ERROR_MESSAGE_SERVER;
                    }
                    errorSingleton.setErrorMessage(errorMessage[0]);
                });

        this.fearAndGreedApiSingleton.setStringRequest(stringFearAndGreedRequest);
    }
}
