package com.example.covid_19.ui.world;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.covid_19.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A Fragment that shows the world stats section
 *
 */
public class WorldFragment extends Fragment {

    TextView Tcases,TcasesI,Tdeaths,TdeathsI,Trecovered,TrecoveredI;

    public static final String TAG = "MyTag";
    RequestQueue requestQueue;

    /**
     * Called to have the fragment instantiate its user interface view
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     *                  The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return View Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    /**
     * Called when all saved state has been restored into the view hierarchy of the fragment.
     * This can be used to do initialization based on saved state that you are letting the view hierarchy track itself,
     * such as whether check box widgets are currently checked.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Tcases = (TextView) view.findViewById(R.id.CasesN);
        TcasesI = (TextView) view.findViewById(R.id.CasesI);
        Tdeaths = (TextView) view.findViewById(R.id.DeathN);
        TdeathsI = (TextView) view.findViewById(R.id.DeathI);
        Trecovered = (TextView) view.findViewById(R.id.RecoveredN);
        TrecoveredI = (TextView) view.findViewById(R.id.RecoveredI);

        //chart
        WebView myWebView = (WebView) view.findViewById(R.id.webView);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.loadUrl("https://lamp.cse.fau.edu/~yfeng2016/covidchart/WorldChart.html");


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();

        String url = "https://api.covid19api.com/summary";
        // Formulate the request and handle the response.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject global = response.getJSONObject("Global");

                            String cases = global.getString ("TotalConfirmed");
                            String casesI = global.getString ("NewConfirmed");
                            String deaths = global.getString ("TotalDeaths");
                            String deathsI = global.getString ("NewDeaths");
                            String recovered = global.getString ("TotalRecovered");
                            String recoveredI = global.getString ("NewRecovered");

                            Tcases.setText(cases);
                            TcasesI.setText("+"+casesI);
                            Tdeaths.setText(deaths);
                            TdeathsI.setText("+"+deathsI);
                            Trecovered.setText(recovered);
                            TrecoveredI.setText("+"+recoveredI);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        // Set the tag on the request.
        jsonObjectRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);

    }
}
