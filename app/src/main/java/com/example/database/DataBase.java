package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private ArrayList<Comentari> comList;
    private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, "COMENTARIS", null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREA_TAULA = "CREATE TABLE COMENTARIS" +
                "(ID INTEGER PRIMARY KEY, TITOL TEXT, COMMENT TEXT)";
        db.execSQL(CREA_TAULA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS COMENTARIS");
    }

    public void addComment(String titol, String comment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("titol", titol);
        contentValues.put("comment", comment);
        db.insert("COMENTARIS", null, contentValues);
    }

    public void removeProdct(int id) {
        String[] args = new String[] { String.valueOf(id)};
        db.delete("COMMENTARIS", "ID=?", args);
    }

    public ArrayList<Comentari> getComentaris() {
        comList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT ID, TITOL, COMMENT FROM COMENTARIS", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String titol = cursor.getString(1);
                    String comment = cursor.getString(2);

                    Comentari item = new Comentari(id, titol, comment);

                    comList.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return comList;
    }
}
