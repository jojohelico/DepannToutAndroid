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

import bdd.InterventionDAO;

public class GestionInter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_inter);
        initialisation();
        afficherLesInterventions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        afficherLesInterventions(); // Rafraichis la page
    }

    private void initialisation() {
        // gestion du boutton Quitter
        Button btQuitterInter = (Button) findViewById(R.id.btQuitterInter);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitterInter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        // gestion du boutton Gestion des interventions
        Button btCreateInter = (Button) findViewById(R.id.btCreerInter);
        btCreateInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demarrerCreerInter();
            }
        });
    }

    private void demarrerCreerInter(){
        Intent intent = new Intent(GestionInter.this, CreateInter.class);
        startActivity(intent);
    }

    public void afficherLesInterventions(){
        Log.d("bdd", "Début de afficherLesInterventions");
        InterventionDAO interventionDAO = new InterventionDAO(this);
        Cursor c = interventionDAO.readLesInterventions();
        Toast.makeText(this, "Il y a " +
                c.getCount() + " interventions", Toast.LENGTH_SHORT).show();
        String[] from = new String[]{"date", "heure", "observation", "idCli"};
        int[] to = new int[] {R.id.tvDateLigne, R.id.tvHeureLigne, R.id.tvObsLigne, R.id.tvIdClientInterLigne};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.ligne_intervention, c, from, to, 0);
        ListView lvInterventions = (ListView) findViewById(R.id.lvClients);
        lvInterventions.setAdapter(dataAdapter);

        interventionDAO.close();

        // Set an item click listener for the ListView
        lvInterventions.setOnItemClickListener((parent, view, position, id) -> {
            // Move the Cursor to the clicked item's position
            c.moveToPosition(position);

            // Extract details from the cursor
            int idInter = c.getInt(c.getColumnIndexOrThrow("_id"));
            String date = c.getString(c.getColumnIndexOrThrow("date"));
            String heure = c.getString(c.getColumnIndexOrThrow("heure"));
            String observation = c.getString(c.getColumnIndexOrThrow("observation"));
            int idCli = c.getInt(c.getColumnIndexOrThrow("idCli"));

            // Pass the details to the new activity using Intent
            Intent intent = new Intent(this, ModifInter.class);
            intent.putExtra("_id", idInter);
            intent.putExtra("date", date);
            intent.putExtra("heure", heure);
            intent.putExtra("observation", observation);
            intent.putExtra("idCli", idCli);
            startActivity(intent);
        });
    }


}