package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import metier.Client;

public class ClientDAO {
    private DAO dao = null;
    private SQLiteDatabase db = null;

    /**
     * Constructeur
     * @param context
     */

    public ClientDAO(Context context){
        dao = new DAO(context);
        db = dao.open();
    }

    /**
     * Fermeture de la base de données
     */

    public void close() {
        dao.close();
    }

    public Cursor readLesClients() {
        String reqSql = "Select id as '_id', nom, prenom, numTel, adrMail, adrPostale FROM " + CreateBdDepannTout.TABLE_CLIENT +";";
        // Execution de la requête
        Cursor c = db.rawQuery(reqSql, null);
        Log.d("bdd", "le curseur contient " + c.getCount() + " lignes");
        return c;
    }

    public long create(Client c) {
        ContentValues values = new ContentValues();
        values.put("nom", c.getNom());
        values.put("prenom", c.getPrenom());
        values.put("numTel", c.getNumTel());
        values.put("adrMail", c.getAdrMail());
        values.put("adrPostale", c.getAdrPostale());
        Log.d("bdd", "insert, :" + c);
        return db.insert(CreateBdDepannTout.TABLE_CLIENT, null, values);
    }

    public boolean delete(int id) {
        int result = db.delete(CreateBdDepannTout.TABLE_CLIENT,
                "id = ?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean update(int id, String nom, String prenom, String numTel, String adrMail, String adrPostale) {
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("numTel", numTel);
        values.put("adrMail", adrMail);
        values.put("adrPostale", adrPostale);
        int result = db.update(CreateBdDepannTout.TABLE_CLIENT,
                values,
                "id = ?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }

}
