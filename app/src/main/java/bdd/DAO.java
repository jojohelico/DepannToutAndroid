package bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAO {
    private static final int VERSION_BDD = 9;
    private static final String NOM_BDD = "bdDepannTout";
    private CreateBdDepannTout createBd = null;
    private SQLiteDatabase db = null;

    public DAO(Context context){
        createBd = new CreateBdDepannTout(context, NOM_BDD, null, VERSION_BDD);
        Log.d("bdd", "Appel au constructeur de DAO ok, bdd créée");
    }

    public SQLiteDatabase open(){
        if (db == null){
            db = createBd.getWritableDatabase();
            Log.d("bdd", "Base de données ouverte");
        } else {
            Log.d("bdd", "base de données accessible");
        }
        return db;
    }

    public void close() {
        if(db != null){
            db.close();
            Log.d("bdd", "Base de données fermée");
        }
    }
}
