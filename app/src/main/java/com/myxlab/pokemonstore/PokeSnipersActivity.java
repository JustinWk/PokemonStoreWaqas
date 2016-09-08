package com.myxlab.pokemonstore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haslina on 8/15/2016.
 */
public class PokeSnipersActivity extends AppCompatActivity {

    //Creating a List of pokemons
    private List<PokeSnipe> pokeSnipeList;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_snipers);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our Pokemons list
        pokeSnipeList = new ArrayList<>();

        //Calling method to get data
        getData();

    }


    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,
                "Loading Data", "Please wait...", false, true);
        String url = "http://pokesnipers.com/api/v1/pokemon.json";


               //Creating a json object request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        loading.dismiss();
                        try {
                            Log.e("Response",response.toString());
                            JSONArray resultsArray = response.getJSONArray("results");
                           //calling method to parse json array
                            parseData(resultsArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);
    }


    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            PokeSnipe snipe = new PokeSnipe();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                snipe.setPokeImageUrl(json.getString("icon"));
                snipe.setTitle(json.getString("name"));
               // albums.setTime(json.getString("until"));
                snipe.setCoord(json.getString("coords"));
                Log.e("PokeSnipers Response("+array.length() +")",json.toString());
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pokeSnipeList.add(snipe);
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(pokeSnipeList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }





}
