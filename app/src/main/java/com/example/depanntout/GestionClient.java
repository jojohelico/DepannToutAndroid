package com.example.depanntout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bdd.ClientDAO;

public class GestionClient extends AppCompatActivity {

    private ClientDAO clientDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_client);
        initialisation();
        afficherLesClients();
    }

    @Override
    protected void onResume() {
        super.onResume();
        afficherLesClients(); // Rafraichis la page
    }


    private void initialisation() {
        // gestion du boutton Quitter
        Button btQuitterClient = (Button) findViewById(R.id.btQuitterInter);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitterClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        // gestion du boutton Gestion des clients
        Button btCreerClient = (Button) findViewById(R.id.btCreerInter);
        btCreerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demarrerCreerClient();
            }
        });
    }

    private void demarrerCreerClient(){
        Intent intent = new Intent(GestionClient.this, CreateClient.class);
        startActivity(intent);
    }

    public void afficherLesClients(){
        Log.d("bdd", "Début de afficherLesClients");
        ClientDAO clientDAO = new ClientDAO(this);
        Cursor c = clientDAO.readLesClients();

        int count =  c.getCount();
        String message = String.format("Il y a %d clients", count);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "123456789123456789123456789", Toast.LENGTH_SHORT).show();

        String[] from = new String[]{"_id", "nom", "prenom", "numTel", "adrMail", "adrPostale"};
        int[] to = new int[] {R.id.tvIdClient, R.id.tvNomClient, R.id.tvPrenomClient, R.id.tvNumTelClient, R.id.tvAdrMailClient, R.id.tvAdrPostClient};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.ligne_client, c, from, to, 0);
        ListView lvClients = (ListView) findViewById(R.id.lvClients);
        lvClients.setAdapter(dataAdapter);

        // Set an item click listener for the ListView
        lvClients.setOnItemClickListener((parent, view, position, id) -> {
            // Move the Cursor to the clicked item's position
            c.moveToPosition(position);

            // Extract details from the cursor
            int idCli = c.getInt(c.getColumnIndexOrThrow("_id"));
            String prenom = c.getString(c.getColumnIndexOrThrow("prenom"));
            String nom = c.getString(c.getColumnIndexOrThrow("nom"));
            int numTel = c.getInt(c.getColumnIndexOrThrow("numTel"));
            String adrMail = c.getString(c.getColumnIndexOrThrow("adrMail"));
            String adrPostale = c.getString(c.getColumnIndexOrThrow("adrPostale"));


            // Pass the details to the new activity using Intent
            Intent intent = new Intent(this, ModifClient.class);
            intent.putExtra("_id", idCli);
            intent.putExtra("prenom", prenom);
            intent.putExtra("nom", nom);
            intent.putExtra("numTel", numTel);
            intent.putExtra("adrMail", adrMail);
            intent.putExtra("adrPostale", adrPostale);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        if (clientDAO != null) {
            clientDAO.close(); // ✅ safely close DB connection here
        }
        super.onDestroy(); // always call the parent version!
    }
}