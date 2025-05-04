package com.example.depanntout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bdd.ClientDAO;
import bdd.InterventionDAO;
import metier.Client;
import metier.Intervention;

public class CreateInter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inter);
        initialisation();
        gererCreateInter();
    }

    private void initialisation() {
        // gestion du boutton Quitter
        Button btQuitterCreate = (Button) findViewById(R.id.btQuitterCreate);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitterCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                interventionDAO.close();
                finish();
            }
        });
    }
    private InterventionDAO interventionDAO = null;
    Intervention inter = null;


    // Contrôle graphiques
    private EditText etDate;
    private EditText etHeure;
    private EditText etObs;
    private EditText etIdCli;
    private Button btCreateInter;
    private Spinner spinnerClient;
    private List<Client> clients;
    private Map<String, Integer> clientNameToIdMap = new HashMap<>();

    //Valeur saisies
    String date;
    String heure;
    String observation;
    int idCli;

    private void gererCreateInter() {
        // Reconnaissance des contrôles graphiques de la vue
        spinnerClient = findViewById(R.id.spinnerClient);
        etDate = (EditText) findViewById(R.id.ptSaisiDate);
        etHeure = (EditText) findViewById(R.id.ptSaisiHeure);
        etObs = (EditText) findViewById(R.id.ptSaisiObserv);
        btCreateInter = (Button) findViewById(R.id.btCreateInter);

        // Accès à la table article
        interventionDAO = new InterventionDAO(this);
        // Load clients from database
        ClientDAO clientDAO = new ClientDAO(this);
        Cursor c = clientDAO.readLesClients();
        List<String> clientNames = new ArrayList<>();
        clients = new ArrayList<>();

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow("_id"));
            String nom = c.getString(c.getColumnIndexOrThrow("nom"));
            String prenom = c.getString(c.getColumnIndexOrThrow("prenom"));
            String displayName = prenom + " " + nom;

            clientNames.add(displayName);
            clientNameToIdMap.put(displayName, id);
        }

        c.close();
        clientDAO.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClient.setAdapter(adapter);

        // Gestion de l'événement onClick sur le boutn Ajouter
        btCreateInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                date = etDate.getText().toString();
                heure = etHeure.getText().toString();
                observation = etObs.getText().toString();
                String selectedClientName = spinnerClient.getSelectedItem().toString();
                idCli = clientNameToIdMap.get(selectedClientName);

                inter = new Intervention(date, heure, observation, idCli);
                long idInterCree = interventionDAO.create(inter);

                Toast.makeText(getApplicationContext(), "Intervention ajoutée", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateInter.this, GestionInter.class);
                startActivity(intent);
                finish();
            }
        });
    }
}