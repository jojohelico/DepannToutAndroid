package com.example.depanntout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bdd.ClientDAO;
import metier.Client;

public class CreateClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);
        initialisation();
        gererCreateClient();
    }

    private void initialisation() {
        // gestion du boutton Quitter
        Button btQuitterCreate = (Button) findViewById(R.id.btQuitterCreate);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitterCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                clientDAO.close();
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
    private Button btCreateClient;

    //Valeur saisies
    String nom;
    String prenom;
    String numTel;
    String adrMail;
    String adrPostale;

    private void gererCreateClient() {
        // Reconnaissance des contrôles graphiques de la vue
        etNom = (EditText) findViewById(R.id.ptSasirNom);
        etPrenom = (EditText) findViewById(R.id.ptSaisirPrenom);
        etNumTel = (EditText) findViewById(R.id.ptSaisirNumTel);
        etAdrMail = (EditText) findViewById(R.id.ptSaisirMailClient);
        etAdrPostale = (EditText) findViewById(R.id.ptSaisirAdrClient);
        btCreateClient = (Button) findViewById(R.id.btCreateClient);
        // Accès à la table client
        clientDAO = new ClientDAO(this);
        // Gestion de l'événement onClick sur le boutn Ajouter
        btCreateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Récupération des zones saisies
                nom = etNom.getText().toString();
                prenom = etPrenom.getText().toString();
                numTel = etNumTel.getText().toString();
                adrMail = etAdrMail.getText().toString();
                adrPostale = etAdrPostale.getText().toString();
                // Création de l'article correspondant
                cli = new Client(nom, prenom, numTel, adrMail, adrPostale);
                // Insertion de l'article dans la base de données
                long idClientCree = clientDAO.create(cli);
                // Message à l'écran
                Toast.makeText(getApplicationContext(), "Client ajouté ! ", Toast.LENGTH_SHORT).show();
                clientDAO.close();
                // Redirection vers la liste des clients
                Intent intent = new Intent(CreateClient.this, GestionClient.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
