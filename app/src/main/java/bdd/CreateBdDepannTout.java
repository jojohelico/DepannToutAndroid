package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CreateBdDepannTout extends SQLiteOpenHelper {

    public static final String TABLE_CLIENT = "client";
    public static final String TABLE_INTER = "intervention";
    private static final String CREATE_TABLE_CLIENT =
            "CREATE TABLE " + TABLE_CLIENT + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "prenom TEXT NOT NULL, " +
                    "numTel TEXT NOT NULL, " +
                    "adrMail TEXT NOT NULL, " +
                    "adrPostale TEXT NOT NULL);";

    private static final String CREATE_TABLE_INTER =
            "CREATE TABLE " + TABLE_INTER + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idCli TEXT NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "heure TEXT NOT NULL," +
                    "observation TEXT NOT NULL);";

    private static final String INSERT_INTO_INTERVENTION =
            "INSERT INTO " + TABLE_INTER + " (idCli, date, heure, observation) VALUES " +
                    "(1, '03/01/2025', '12h30', 'dégât des eaux dans la cuisine'), " +
                    "(2, '03/01/2025', '13h00', 'dégât des eaux dans la salle de bain'), " +
                    "(3, '03/01/2025', '14h15', 'fuite d''eau dans le salon'), " +
                    "(4, '03/01/2025', '15h45', 'dégât des eaux dans le garage'), " +
                    "(5, '03/01/2025', '16h00', 'fuite d''eau dans la chambre'), " +
                    "(6, '03/01/2025', '17h30', 'dégât des eaux sur la terrasse'), " +
                    "(7, '03/01/2025', '18h10', 'fuite d''eau dans les toilettes'), " +
                    "(8, '03/01/2025', '19h00', 'dégât des eaux dans le bureau'), " +
                    "(9, '03/01/2025', '20h15', 'fuite d''eau dans l''entrée'), " +
                    "(10, '03/01/2025', '21h00', 'dégât des eaux dans la buanderie')";

    private static final String INSERT_INTO_CLIENT =
            "INSERT INTO " + TABLE_CLIENT + " (nom, prenom, numTel, adrMail, adrPostale) VALUES " +
                    "('Dupont', 'Jean', '0600000001', 'jean.dupont@example.com', '10 Rue de Paris, 75001 Paris'), " +
                    "('Martin', 'Sophie', '0600000002', 'sophie.martin@example.com', '15 Rue de Lyon, 69001 Lyon'), " +
                    "('Durand', 'Paul', '0600000003', 'paul.durand@example.com', '20 Rue de Bordeaux, 33000 Bordeaux'), " +
                    "('Lemoine', 'Claire', '0600000004', 'claire.lemoine@example.com', '25 Rue de Lille, 59000 Lille'), " +
                    "('Morel', 'Luc', '0600000005', 'luc.morel@example.com', '30 Rue de Nantes, 44000 Nantes'), " +
                    "('Blanc', 'Julie', '0600000006', 'julie.blanc@example.com', '35 Rue de Marseille, 13000 Marseille'), " +
                    "('Garcia', 'Emma', '0600000007', 'emma.garcia@example.com', '40 Rue de Toulouse, 31000 Toulouse'), " +
                    "('Petit', 'Hugo', '0600000008', 'hugo.petit@example.com', '45 Rue de Strasbourg, 67000 Strasbourg'), " +
                    "('Roux', 'Chloe', '0600000009', 'chloe.roux@example.com', '50 Rue de Nice, 06000 Nice'), " +
                    "('Fournier', 'Lucas', '0600000010', 'lucas.fournier@example.com', '55 Rue de Rennes, 35000 Rennes')";

    // Constructeur, à générer automatiquement
    public CreateBdDepannTout(@Nullable Context context, @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Création de la base de données si elle n'existe pas
     *
     * @param db base
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENT);
        Log.d("bdd", "table client créée");

        db.execSQL(CREATE_TABLE_INTER);
        Log.d("bdd", "table intervention créée");

        db.execSQL(INSERT_INTO_INTERVENTION);
        Log.d("bdd", "données table intervention inserées");

        db.execSQL(INSERT_INTO_CLIENT);
        Log.d("bdd", "données table client inserées");
    }

    /**
     * Création d'une nouvelle base en cas de changement de version
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("bdd", "Changement de version : " + newVersion);
        db.execSQL("DROP TABLE " + TABLE_CLIENT + ";");
        Log.d("bdd", "Table " + TABLE_CLIENT + " supprimée");
        db.execSQL("DROP TABLE " + TABLE_INTER + ";");
        Log.d("bdd", "Table " + TABLE_INTER + " supprimée");
        onCreate(db);
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Ne rien supprimer automatiquement !
//        Log.d("bdd", "Mise à jour de la base ignorée : old=" + oldVersion + " new=" + newVersion);
//    }

    public void insertDataInter(SQLiteDatabase db) {
        // Sample data to insert
        String[][] data = {
                {"1", "03/01/2025", "12h30", "dégât des eaux dans la cuisine"},
                {"2", "03/01/2025", "13h00", "dégât des eaux dans la salle de bain"},
                {"3", "03/01/2025", "14h15", "fuite d'eau dans le salon"},
                {"4", "03/01/2025", "15h45", "dégât des eaux dans le garage"},
                {"5", "03/01/2025", "16h00", "fuite d'eau dans la chambre"},
                {"6", "03/01/2025", "17h30", "dégât des eaux sur la terrasse"},
                {"7", "03/01/2025", "18h10", "fuite d'eau dans les toilettes"},
                {"8", "03/01/2025", "19h00", "dégât des eaux dans le bureau"},
                {"9", "03/01/2025", "20h15", "fuite d'eau dans l'entrée"},
                {"10", "03/01/2025", "21h00", "dégât des eaux dans la buanderie"}
        };

        // Insert each row into the database
        for (String[] row : data) {
            ContentValues values = new ContentValues();
            values.put("idCli", row[0]);
            values.put("date", row[1]);
            values.put("heure", row[2]);
            values.put("observation", row[3]);

            db.insert("Intervention", null, values); // Insert the row
            Log.d("bdd", "Insertion intervention réussie " + values);
        }
    }

}
