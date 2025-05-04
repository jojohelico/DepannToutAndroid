package com.example.depanntout;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bdd.ClientDAO;
import bdd.InterventionDAO;
import metier.Intervention;

public class ModifInter extends AppCompatActivity {
    private Spinner spinnerClient;
    private Map<String, Integer> clientNameToIdMap = new HashMap<>();
    private List<String> clientNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_inter);

        initialisation();
    }

    private void initialisation() {

        // Get data from Intent
        String date = getIntent().getStringExtra("date");
        String heure = getIntent().getStringExtra("heure");
        String observation = getIntent().getStringExtra("observation");
        int idCli = getIntent().getIntExtra("idCli", -1);
        int idInter = getIntent().getIntExtra("_id", -1);

        // Reference TextViews
        TextView etDate = findViewById(R.id.ptModifDate);
        TextView etHeure = findViewById(R.id.ptModifHeure);
        TextView etObservation = findViewById(R.id.ptModifObserv);
        spinnerClient = findViewById(R.id.spinnerClientModif);

        // Set data in TextViews
        etDate.setText(date);
        etHeure.setText(heure);
        etObservation.setText(observation);

        // Load clients into spinner
        ClientDAO clientDAO = new ClientDAO(this);
        Cursor c = clientDAO.readLesClients();
        int selectedIndex = 0;
        int i = 0;
        while (c.moveToNext()) {
            int cliId = c.getInt(c.getColumnIndexOrThrow("_id"));
            String prenom = c.getString(c.getColumnIndexOrThrow("prenom"));
            String nom = c.getString(c.getColumnIndexOrThrow("nom"));
            String displayName = prenom + " " + nom;
            clientNames.add(displayName);
            clientNameToIdMap.put(displayName, cliId);

            if (cliId == idCli) {
                selectedIndex = i;  // Remember selected index to show in spinner
            }
            i++;
        }

        c.close();
        clientDAO.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClient.setAdapter(adapter);
        spinnerClient.setSelection(selectedIndex);

        // gestion du boutton Quitter
        Button btQuitterInter = (Button) findViewById(R.id.btQuitterInter);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitterInter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        Button btnDeleteInter = findViewById(R.id.btnDeleteInter);
        Button btnModifInter = findViewById(R.id.btnModifInter);

        // Accès à la table intervention
        InterventionDAO interventionDAO = new InterventionDAO(this);

        // Gestion de l'événement onClick sur le boutn Ajouter
        btnDeleteInter.setOnClickListener(v -> {
            boolean isDeleted = interventionDAO.delete(idCli);
            interventionDAO.close();
            if (isDeleted) {
                Toast.makeText(this, "Client deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnModifInter.setOnClickListener(v -> {

            String dateModif = etDate.getText().toString();
            String heureModif = etHeure.getText().toString();
            String obsModif = etObservation.getText().toString();
            String selectedName = spinnerClient.getSelectedItem().toString();
            int idCliModif = clientNameToIdMap.get(selectedName);

            boolean isUpdated = interventionDAO.update(idInter, dateModif, heureModif, obsModif, idCliModif);
            if (isUpdated) {
                Toast.makeText(this, "Intervention modifiée", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erreur modification", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
