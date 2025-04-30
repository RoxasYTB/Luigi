package com.example.appli20240829;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appli20240829.config.Constants;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerLink, forgotPasswordLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        registerLink = findViewById(R.id.textViewCreateAccount);
        forgotPasswordLink = findViewById(R.id.textViewForgotPassword);

        // Ajout Spinner pour le choix de l'URL
        Spinner spinnerBaseUrl = findViewById(R.id.spinnerBaseUrl);
        String[] urls = {"192.168.8.80:8180", "10.0.0.2:8180"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, urls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBaseUrl.setAdapter(adapter);

        // Charger la valeur sauvegardée
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUrl = prefs.getString("base_url", "192.168.8.80:8180");
        int selectedIndex = 0;
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].equals(savedUrl)) {
                selectedIndex = i;
                break;
            }
        }
        spinnerBaseUrl.setSelection(selectedIndex);

        // Sauvegarder le choix à chaque modification
        spinnerBaseUrl.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedUrl = urls[position];
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                editor.putString("base_url", selectedUrl);
                editor.apply();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        loginButton.setOnClickListener(view -> new LoginTask().execute(emailInput.getText().toString(), passwordInput.getText().toString()));
        registerLink.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
        forgotPasswordLink.setOnClickListener(view -> Toast.makeText(this, "Fonctionnalité à implémenter", Toast.LENGTH_SHORT).show());
    }

    public class LoginTask extends AsyncTask<String, Void, String> {
        private int customerId;

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            try {
                URL url = new URL(Constants.getToadCustomerLoginUrl(MainActivity.this) + "?email=" + email);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                connection.disconnect();

                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.getString("password").equals(password)) {
                    customerId = jsonResponse.getInt("customerId");

                    // ✅ Stocker le customer_id AVANT le return
                    SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("customerId", customerId);
                    editor.apply();

                    return "success";
                } else {
                    return "Mot de passe incorrect";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Erreur de connexion";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("success")) {
                Log.d("MainActivity", "Connexion réussie, lancement de l'activité suivante.");
                Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AfficherListeDvdsActivity.class));
            } else {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

}


