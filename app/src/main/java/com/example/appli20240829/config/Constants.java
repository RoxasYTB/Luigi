package com.example.appli20240829.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Classe de configuration contenant les constantes de l'application,
 * notamment les URLs des services web.
 */
public class Constants {

    // Environnement à utiliser : DEV, TEST ou PROD
    public static final String ENVIRONMENT = "DEV";

    // Base URL dynamique selon le choix utilisateur
    public static String getToadBaseUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String ip = prefs.getString("base_url", "192.168.8.80:8180");
        return "http://" + ip + "/toad/";
    }

    // Méthodes qui nécessitent un Context pour obtenir l'URL dynamique
    public static String getToadCustomerRegisterUrl(Context context) {
        return getToadBaseUrl(context) + "customer/add";
    }

    public static String getToadCustomerLoginUrl(Context context) {
        return getToadBaseUrl(context) + "customer/getByEmail";
    }

    public static String getToadFilmListUrl(Context context) {
        return getToadBaseUrl(context) + "film/all";
    }

    public static String getToadInventoryAvailableByIdUrl(Context context, int id) {
        return getToadBaseUrl(context) + "inventory/available/getById?id=" + id;
    }

    public static String getToadInventoryStockUrl(Context context) {
        return getToadBaseUrl(context) + "inventory/stock";
    }

    public static String getToadInventoryStockByFilmUrl(Context context) {
        return getToadBaseUrl(context) + "inventory/stock/byFilm";
    }

    public static String getToadRentalAddUrl(Context context) {
        return getToadBaseUrl(context) + "rental/add";
    }
}
