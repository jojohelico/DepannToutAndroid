package com.example.depanntout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import bdd.CreateBdDepannTout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialisation();

        // Create an instance of your database helper
        CreateBdDepannTout dbHelper = new CreateBdDepannTout(this, "DepannTout.db", null, 1);

        // Open the database in writable mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Call the insertData function to populate your table
//        dbHelper.insertDataInter(db);
//        Log.d("MainActivity", "Data inserted into the intervention table");
//
//        dbHelper.insertClientData(db);
//        Log.d("MainActivity", "Data inserted into the client table");
    }

    private void initialisation() {
        // gestion du boutton Quitter
        Button btQuitter = (Button) findViewById(R.id.btQuitter);
        // Association d'un écouteur d'événement au clic de btQuitter
        btQuitter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                finish();
            }
        });

        // gestion du boutton Gestion des clients
        Button btGestClient = (Button) findViewById(R.id.btGestClient);
        btGestClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demarrerClient();
            }
        });

        // gestion du boutton Gestion des Interventions
        Button btGestInter = (Button) findViewById(R.id.btGestInter);
        btGestInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demarrerInter();
            }
        });
    }

    private void demarrerClient(){
        Intent intent = new Intent(MainActivity.this, GestionClient.class);
        startActivity(intent);
    }

    private void demarrerInter() {
        Intent intent = new Intent(MainActivity.this, GestionInter.class);
        startActivity(intent);
    }
}