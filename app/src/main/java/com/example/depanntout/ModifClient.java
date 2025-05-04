package com.example.depanntout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bdd.ClientDAO;
import metier.Client;

public class ModifClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_client);
        initialisation();
    }

    private void initialisation() {

        String prenom = getIntent().getStringExtra("prenom");
        String nom = getIntent().getStringExtra("nom");
        int numTel = getIntent().getIntExtra("numTel", -1);
        String adrMail = getIntent().getStringExtra("adrMail");
        String adrPostale = getIntent().getStringExtra("adrPostale");
        idCli = getIntent().getIntExtra("_id", -1);

        EditText etPrenom = findViewById(R.id.ptModifPrenom);
        EditText etNom = findViewById(R.id.ptModifNom);
        EditText etNumTel = findViewById(R.id.ptModifNumTel);
        EditText etAdrMail = findViewById(R.id.ptModifAdrMail);
        EditText etAdrPostale = findViewById(R.id.ptModifAdrPostale);

        etPrenom.setText(prenom);
        etNom.setText(nom);
        etNumTel.setText(numTel != -1 ? String.valueOf(numTel) : "");
        etAdrMail.setText(adrMail);
        etAdrPostale.setText(adrPostale);

        Button btnDeleteClient = findViewById(R.id.btnDeleteClient);
        Button btnModifClient = findViewById(R.id.btnModifClient);

        // Accès à la table client
        clientDAO = new ClientDAO(this);

        // Gestion de l'événement onClick sur le boutn Ajouter
        btnDeleteClient.setOnClickListener(v -> {
            boolean isDeleted = clientDAO.delete(idCli);
            clientDAO.close();
            if (isDeleted) {
                Toast.makeText(this, "Client deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnModifClient.setOnClickListener(v -> {

            String prenomModif = etPrenom.getText().toString();
            String nomModif = etNom.getText().toString();
            int numTelModif = Integer.parseInt(etNumTel.getText().toString());
            String adrMailModif = etAdrMail.getText().toString();
            String adrPostaleModif = etAdrPostale.getText().toString();

            Log.d("UpdateDebug", idCli + nomModif + prenomModif + numTelModif + adrMailModif + adrPostaleModif);
            boolean isUpdated = clientDAO.update(idCli, nomModif, prenomModif, numTelModif, adrMailModif, adrPostaleModif);
            if (isUpdated) {
                Toast.makeText(this, "Client updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        // gestion du boutton Quitter
        Button btQuitterInter = (Button) findViewById(R.id.btQuitterClient);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitterInter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    private ClientDAO clientDAO = null;
    Client cli = null;

    // Contrôle graphiques
    private EditText etNom;
    private EditText etPrenom;
    private EditText etNumTel;
    private EditText etAdrMail;
    private EditText etAdrPostale;
    private Button btnDeleteClient;

    //Valeur saisies
    Integer idCli;
    String nom;
    String prenom;
    Integer numTel;
    String adrMail;
    String adrPostale;
}
