package ifrs.canoas.model.portal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ifrs.canoas.lib.BancoHelper;

/**
 * Created by developer on 18/09/17.
 */

public class Note {
    private int idNota;
    private String titulo;
    private String data;
    private String texto;
    private String disciplina;

    public Note() {
    }



    public Note(int idNota) {
        this.idNota = idNota;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void insert(BancoHelper BancoHelper){
        // Gets the data repository in write mode
        SQLiteDatabase db = BancoHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITULO, titulo);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TEXTO, texto);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DATA, data);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DISCIPLINA, disciplina);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);

    }

    public void update(BancoHelper BancoHelper){
        SQLiteDatabase db = BancoHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITULO, titulo);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TEXTO, texto);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DATA, data);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_DISCIPLINA, disciplina);


        // Which row to update, based on the title
        String whereCondition = NoteContract.NoteEntry._ID + " = ?";
        String[] whereConditionArgs = { String.valueOf(this.idNota) };//isso é um Vetor

        int count = db.update(
                NoteContract.NoteEntry.TABLE_NAME,
                values,
                whereCondition,
                whereConditionArgs);
    }

    public void delete(BancoHelper BancoHelper){
        SQLiteDatabase db = BancoHelper.getReadableDatabase();
        // Define 'where' part of query.
        String whereCondition = NoteContract.NoteEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] whereConditionArgs = { String.valueOf(this.idNota) };
        // Issue SQL statement.
        db.delete(NoteContract.NoteEntry.TABLE_NAME, whereCondition, whereConditionArgs);

    }

    public void load(BancoHelper BancoHelper){
        SQLiteDatabase db = BancoHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TITULO,
                NoteContract.NoteEntry.COLUMN_NAME_TEXTO,
                NoteContract.NoteEntry.COLUMN_NAME_DATA,
                NoteContract.NoteEntry.COLUMN_NAME_DISCIPLINA
        };

        // Filter results WHERE "title" = 'My Title'
        String whereCondition = NoteContract.NoteEntry._ID + " = ?";
        String[] whereConditionArgs = { String.valueOf(this.idNota) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                NoteContract.NoteEntry.COLUMN_NAME_TITULO + " DESC";

        Cursor c = db.query(
                NoteContract.NoteEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                whereCondition,                                // The columns for the WHERE clause
                whereConditionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();
        this.idNota = c.getInt(c.getColumnIndex(NoteContract.NoteEntry._ID));
        this.data = c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_DATA));
        this.disciplina = c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_DISCIPLINA));
        this.texto = c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_TEXTO));

    }

    public static List<Note> getAll(BancoHelper BancoHelper) {
        SQLiteDatabase db = BancoHelper.getReadableDatabase();
        List<Note> list = new ArrayList<>();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TITULO,
                NoteContract.NoteEntry.COLUMN_NAME_TEXTO,
                NoteContract.NoteEntry.COLUMN_NAME_DATA,
                NoteContract.NoteEntry.COLUMN_NAME_DISCIPLINA
        };

        String sortOrder =
                NoteContract.NoteEntry.COLUMN_NAME_TITULO + " DESC";

        Cursor c = db.query(
                NoteContract.NoteEntry.TABLE_NAME,   // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        if (c.getCount() > 0) {
            c.moveToFirst();

            do {
                Note nt = new Note();
                nt.setIdNota(c.getInt(c.getColumnIndex(NoteContract.NoteEntry._ID)));
                nt.setData(c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_DATA)));
                nt.setDisciplina(c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_DISCIPLINA)));
                nt.setTexto(c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_TEXTO)));
                nt.setTitulo(c.getString(c.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_TITULO)));
                list.add(nt);
            }while(c.moveToNext());
        }
        return list;
    }

    public String listToString(List<Note> list) {
        String result = "";
        for (Note note : list) {
            result += note.toString() + "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return "Title: " + titulo + "\tDate: " + data + "\tText: " + texto + "\tDiscipline: " + disciplina + "\n";
    }

    /**
     * WTF public final static
     *
     * Nada mais que uma boa prática para dizer que aqui vai ter o contrato da tabela do banco
     * A classe ficou interna a note devido a fazer parte do model e fazer mais sentido a existência dela aqui
     * dessa forma removi a classe que a documentação oficial sugere.
     * 
     * Como você pode simplificar isso? 
     * E se simplificar que desvantagem você vai ter.
     */
    public final static class NoteContract {
         public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                        NoteEntry._ID + " INTEGER PRIMARY KEY," +
                        NoteEntry.COLUMN_NAME_TEXTO + "TEXT, " +
                        NoteEntry.COLUMN_NAME_DATA + "INTEGER, " +
                        NoteEntry.COLUMN_NAME_DISCIPLINA + "TEXT, "  +
                        NoteEntry.COLUMN_NAME_TITULO + "TEXT )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;

        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private NoteContract() {}

        /* Inner class that defines the table contents */
        public static class NoteEntry implements BaseColumns {
            public static final String TABLE_NAME = "notas";
            public static final String COLUMN_NAME_TITULO= "titulo";
            public static final String COLUMN_NAME_DATA = "data";
            public static final String COLUMN_NAME_TEXTO = "texto";
            public static final String COLUMN_NAME_DISCIPLINA = "disciplina";
        }
    }

}
