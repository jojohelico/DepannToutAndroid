package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import metier.Intervention;

public class InterventionDAO {
    private DAO dao = null;
    private SQLiteDatabase db = null;

    /**
     * Constructeur
     * @param context
     */

    public InterventionDAO(Context context){
        dao = new DAO(context);
        db = dao.open();
    }

    /**
     * Fermeture de la base de données
     */

    public void close() {
        dao.close();
    }

    public Cursor readLesInterventions() {
        String reqSql = "Select id as '_id', " +
                "       date," +
                "       heure," +
                "       observation," +
                "       idCli " +
                "FROM " + CreateBdDepannTout.TABLE_INTER + ";";
        // Execution de la requête
        Cursor c = db.rawQuery(reqSql, null);
        Log.d("bdd", "le curseur contient " + c.getCount() + " lignes");
        return c;
    }

    public long create(Intervention i) {
        ContentValues values = new ContentValues();
        values.put("date", i.getDate());
        values.put("heure", i.getHeure());
        values.put("idCli", i.getIdCli());
        values.put("observation", i.getObservation());
        Log.d("bdd", "insert, :" + i);
        return db.insert(CreateBdDepannTout.TABLE_INTER, null, values);
    }

    public boolean delete(int id) {
        int result = db.delete(CreateBdDepannTout.TABLE_INTER,
                "id = ?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean update(int id, String date, String heure, String observation, int idCli) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("heure", heure);
        values.put("observation", observation);
        values.put("idCli", idCli);
        int result = db.update(CreateBdDepannTout.TABLE_INTER,
                values,
                "id = ?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }

}
