package com.example.depanntout;

import android.content.Context;
import android.database.Cursor;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import bdd.InterventionDAO;
import metier.Intervention;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InterventionDAOTest {

    private InterventionDAO interventionDAO;

    // Initialisation avant chaque test
    @Before
    public void setup() {
        // Récupération du contexte de l'application
        Context context = ApplicationProvider.getApplicationContext();
        interventionDAO = new InterventionDAO(context);
    }

    @Test
    public void testCreerLireIntervention() {
        // Création d'une intervention de test (en supposant que le client 1 existe déjà)
        Intervention inter = new Intervention("04/05/2025", "10:00", "Test observation", 1);

        // Insertion de l'intervention dans la base
        long id = interventionDAO.create(inter);

        // Vérifie que l'insertion s'est bien passée
        assertTrue("Insertion de l'intervention échouée", id != -1);

        // Récupération des interventions depuis la base
        Cursor cursor = interventionDAO.readLesInterventions();

        boolean found = false;

        // Parcours du curseur pour vérifier la présence de l'intervention ajoutée
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String heure = cursor.getString(cursor.getColumnIndexOrThrow("heure"));
            String obs = cursor.getString(cursor.getColumnIndexOrThrow("observation"));
            int idCli = cursor.getInt(cursor.getColumnIndexOrThrow("idCli"));

            // Vérifie si on retrouve les mêmes données que celles insérées
            if (date.equals("04/05/2025") && heure.equals("10:00") &&
                    obs.equals("Test observation") && idCli == 1) {
                found = true;
                break;
            }
        }

        // Fermeture du curseur et de la base
        cursor.close();
        interventionDAO.close();

        // Vérifie que l'intervention a bien été retrouvée
        assertTrue("Intervention non retrouvée dans la base", found);
    }


    @Test
    public void testSupprIntervention() {
        // Création d'une intervention de test (le client avec id 1 doit exister)
        Intervention inter = new Intervention("05/05/2025", "15:00", "À supprimer", 1);
        long id = interventionDAO.create(inter);

        // Vérifie que l'insertion a bien fonctionné
        assertTrue("Insertion échouée", id != -1);

        // Suppression de l'intervention avec son ID
        boolean deleted = interventionDAO.delete((int) id);
        assertTrue("Suppression échouée", deleted);

        // Vérifie que l'intervention a bien disparu de la base
        Cursor cursor = interventionDAO.readLesInterventions();
        boolean found = false;
        while (cursor.moveToNext()) {
            int interId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            if (interId == (int) id) {
                found = true;
                break;
            }
        }

        cursor.close();
        interventionDAO.close();

        // L'intervention ne doit plus être présente
        assertFalse("Intervention toujours présente après suppression", found);
    }
    @Test
    public void testModifIntervention() {
        // ⚠️ Assure-toi qu’un client avec l’ID 1 existe déjà dans la base.
        // Sinon, ajoute un client temporaire ici avant.

        // Création d'une intervention de test (valeurs initiales)
        Intervention inter = new Intervention("01/01/2025", "10:00", "Observation initiale", 1);
        long id = interventionDAO.create(inter);
        assertTrue("Échec de l'insertion de l'intervention", id != -1);

        // Mise à jour des données
        String nouvelleDate = "02/02/2025";
        String nouvelleHeure = "14:30";
        String nouvelleObs = "Observation modifiée";
        int nouveauIdCli = 1;  // Ici, même client. Tu peux en changer si tu veux tester un autre client.

        // Appel de la méthode de mise à jour
        boolean updated = interventionDAO.update((int) id, nouvelleDate, nouvelleHeure, nouvelleObs, nouveauIdCli);
        assertTrue("Échec de la mise à jour de l'intervention", updated);

        // Vérification dans la base que l'intervention a bien été modifiée
        Cursor cursor = interventionDAO.readLesInterventions();
        boolean modifTrouvee = false;

        while (cursor.moveToNext()) {
            int idInter = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String heure = cursor.getString(cursor.getColumnIndexOrThrow("heure"));
            String obs = cursor.getString(cursor.getColumnIndexOrThrow("observation"));

            if (idInter == (int) id &&
                    date.equals(nouvelleDate) &&
                    heure.equals(nouvelleHeure) &&
                    obs.equals(nouvelleObs)) {
                modifTrouvee = true;
                break;
            }
        }

        cursor.close();
        interventionDAO.close();

        // Vérifie que l'intervention modifiée a bien été retrouvée
        assertTrue("Intervention modifiée non retrouvée dans la base", modifTrouvee);
    }

}

