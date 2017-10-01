package ifrs.canoas.lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ifrs.canoas.model.portal.Note;

/**
 * Created by marcio on 17/09/17.
 */

public class BancoHelper extends SQLiteOpenHelper {

    //Famigeradas constantes
    private static final String DATABASE_NAME = "bdzinho";
    private static final int DATABASE_VERSION = 1;

    public BancoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.NoteContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Note.NoteContract.SQL_DELETE_ENTRIES);// hahah ferei com todos os dados do cliente Pense bem antes de fazer isso!
        onCreate(db);
    }
}
