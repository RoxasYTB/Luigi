package com.example.appli20240829.config;

/**
 * Classe de configuration contenant les constantes de l'application,
 * notamment les URLs des services web.
 */
public class Constants {
    
    // Environnement à utiliser : DEV, TEST ou PROD
    public static final String ENVIRONMENT = "DEV";
    
    // URLs par environnement
    private static final String BASE_URL_DEV = "http://10.0.2.2:80/RFTG/serviceWeb/";
    private static final String BASE_URL_TEST = "http://test-server.rftg.com/serviceWeb/";
    private static final String BASE_URL_PROD = "https://production.rftg.com/serviceWeb/";
    
    // Base URL pour l'API Toad
    private static final String TOAD_BASE_URL_DEV = "http://10.0.2.2:8080/toad/";
    private static final String TOAD_BASE_URL_TEST = "http://test-server.toad.com/toad/";
    private static final String TOAD_BASE_URL_PROD = "https://production.toad.com/toad/";
    
    /**
     * Retourne l'URL de base en fonction de l'environnement sélectionné
     */
    public static String getBaseUrl() {
        switch (ENVIRONMENT) {
            case "TEST":
                return BASE_URL_TEST;
            case "PROD":
                return BASE_URL_PROD;
            case "DEV":
            default:
                return BASE_URL_DEV;
        }
    }
    
    /**
     * Retourne l'URL de base de l'API Toad en fonction de l'environnement sélectionné
     */
    public static String getToadBaseUrl() {
        switch (ENVIRONMENT) {
            case "TEST":
                return TOAD_BASE_URL_TEST;
            case "PROD":
                return TOAD_BASE_URL_PROD;
            case "DEV":
            default:
                return TOAD_BASE_URL_DEV;
        }
    }
    
    // Services spécifiques
    public static String getLoginServiceUrl() {
        return getBaseUrl() + "authentification.php";
    }
    
    public static String getRegisterServiceUrl() {
        return getBaseUrl() + "inscription.php";
    }
    
    public static String getDvdListServiceUrl() {
        return getBaseUrl() + "listerDVD.php";
    }
    
    public static String getAddToCartServiceUrl() {
        return getBaseUrl() + "ajouterAuPanier.php";
    }
    
    public static String getCartServiceUrl() {
        return getBaseUrl() + "voirPanier.php";
    }
    
    public static String getValidateCartServiceUrl() {
        return getBaseUrl() + "validerPanier.php";
    }
    
    public static String getRemoveFromCartServiceUrl() {
        return getBaseUrl() + "supprimerDuPanier.php";
    }
    
    // Services API Toad
    public static String getToadCustomerRegisterUrl() {
        return getToadBaseUrl() + "customer/add";
    }
    
    public static String getToadCustomerLoginUrl() {
        return getToadBaseUrl() + "customer/getByEmail";
    }
    
    public static String getToadFilmListUrl() {
        return getToadBaseUrl() + "film/all";
    }
    
    public static String getToadInventoryAvailableByIdUrl(int id) {
        return getToadBaseUrl() + "inventory/available/getById?id=" + id;
    }
    
    public static String getToadInventoryStockUrl() {
        return getToadBaseUrl() + "inventory/stock";
    }
    
    public static String getToadInventoryStockByFilmUrl() {
        return getToadBaseUrl() + "inventory/stock/byFilm";
    }
    
    public static String getToadRentalAddUrl() {
        return getToadBaseUrl() + "rental/add";
    }
}
