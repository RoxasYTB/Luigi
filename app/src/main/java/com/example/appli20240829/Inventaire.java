package com.example.appli20240829;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appli20240829.config.Constants;

import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
public class Inventaire {

    private final List<Dvd> dvdList;
    private final DvdAdapter dvdAdapter;
    private final RequestQueue queue;
    private final Context context; // Ajout du contexte

    // Constructeur
    public Inventaire(Context context, List<Dvd> dvdList, DvdAdapter dvdAdapter) {
        this.dvdList = dvdList;
        this.dvdAdapter = dvdAdapter;
        this.queue = Volley.newRequestQueue(context);
        this.context = context; // Stocker le contexte
    }

    // Méthode pour récupérer les films disponibles
    public void fetchAvailableFilms() {
        JsonArrayRequest request = new JsonArrayRequest(
            Request.Method.GET,
            Constants.getToadInventoryStockByFilmUrl(context), // Utiliser le contexte stocké
            null,
            response -> {
                List<Integer> availableFilmIds = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        availableFilmIds.add(response.getInt(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                updateFilmAvailability(availableFilmIds);
            },
            error -> error.printStackTrace()
        );

        queue.add(request);
    }

    // Mise à jour de la disponibilité des films
    private void updateFilmAvailability(List<Integer> availableFilmIds) {
        for (Dvd dvd : dvdList) {
            dvd.setAvailable(availableFilmIds.contains(dvd.getfilmId()));
        }
        dvdAdapter.notifyDataSetChanged();
    }
}

