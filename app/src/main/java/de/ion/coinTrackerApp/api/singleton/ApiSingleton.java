package de.ion.coinTrackerApp.api.singleton;

import com.android.volley.toolbox.StringRequest;

public interface ApiSingleton {
    /**
     * @return stringRequest
     */
    StringRequest getStringRequest();

    /**
     * @param stringRequest
     */
    void setStringRequest(StringRequest stringRequest);

}
