package com.example.depanntout;
import android.content.Context;
import android.database.Cursor;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import bdd.ClientDAO;
import metier.Client;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class ClientDAOTest {
    private ClientDAO clientDAO;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        clientDAO = new ClientDAO(context);
    }

    @Test
    public void testCreerLireClient() {
        // Création d'un client de test
        Client client = new Client("TestNom", "TestPrenom", "0601010101", "test@mail.com", "123 rue test");
        long id = clientDAO.create(client);

        // Vérifie que l'insertion a bien fonctionné (id != -1)
        assertTrue("Insertion failed", id != -1);

        // Lecture de tous les clients pour vérifier si celui qu'on a inséré est présent
        Cursor cursor = clientDAO.readLesClients();
        boolean found = false;

        // Parcourt les résultats du curseur pour chercher le client qu'on vient d'ajouter
        while (cursor.moveToNext()) {
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
            // Si on trouve un client avec le bon nom et prénom, on le marque comme trouvé
            if (nom.equals("TestNom") && prenom.equals("TestPrenom")) {
                found = true;
                break;
            }
        }

        // Ferme le curseur et la connexion à la base
        cursor.close();
        clientDAO.close();
        assertTrue("Client not found", found);
    }


    @Test
    public void testSupprClient() {
        Client client = new Client("A SUPPR", "PRENOM", "0600000000", "suppr@mail.com", "1 rue suppression");
        long id = clientDAO.create(client);
        assertTrue("Échec de l'insertion", id != -1);

        // Suppression directe avec l'ID
        boolean deleted = clientDAO.delete((int) id);  // delete attend un int
        assertTrue("Échec de la suppression", deleted);

        // Vérifie que le client n’est plus dans la base
        Cursor cursor = clientDAO.readLesClients();
        boolean found = false;
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndexOrThrow("_id")) == id) {
                found = true;
                break;
            }
        }
        cursor.close();
        clientDAO.close();
        assertFalse("Client toujours présent après suppression", found);
    }

    @Test
    public void testModifClient() {
        // Création d'un client de test
        Client client = new Client("NomAvant", "PrenomAvant", "0600000000", "avant@mail.com", "1 rue avant");
        long id = clientDAO.create(client);

        // Vérifie que l'insertion s'est bien passée
        assertTrue("Échec de l'insertion du client", id != -1);

        // Mise à jour des données du client
        boolean updated = clientDAO.update((int) id, "NomApres", "PrenomApres", "0611223344", "apres@mail.com", "2 rue après");

        // Vérifie que la mise à jour a été effectuée
        assertTrue("Échec de la mise à jour du client", updated);

        // Lecture de tous les clients pour vérifier que les nouvelles infos sont bien là
        Cursor cursor = clientDAO.readLesClients();
        boolean foundUpdated = false;

        while (cursor.moveToNext()) {
            int clientId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));

            if (clientId == (int) id && nom.equals("NomApres") && prenom.equals("PrenomApres")) {
                foundUpdated = true;
                break;
            }
        }

        cursor.close();     // On ferme le curseur après utilisation
        clientDAO.close();  // On ferme le DAO proprement

        // Vérifie que les nouvelles données ont bien été retrouvées
        assertTrue("Le client mis à jour n'a pas été retrouvé avec les nouvelles informations", foundUpdated);
    }


}