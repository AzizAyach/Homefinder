package com.ayach.org.homefinder.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ayach.org.homefinder.Entity.Logement;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by aziz on 18/12/2015.
 */
public class Favoris_Dao extends SQLiteOpenHelper{

    private static final  String Tablename ="table_logement";
    private static final String LogementContenu="Logement_col";
    private static final String CREATE_BDD = "CREATE TABLE " + Tablename + " ("
            + LogementContenu  + " TEXT PRIMARY KEY );";

    public Favoris_Dao(Context context) {
        super(context, Tablename, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + Tablename + ";");
        onCreate(db);
    }

    public void addLogement(Logement logement) {
        Gson g = new Gson();

        String j = g.toJson(logement);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LogementContenu,j);
        db.insert(Tablename, null, values);
        db.close();


    }
    public ArrayList<Logement> getAllLogement() {
        ArrayList<Logement> logementList = new ArrayList<Logement>();

        String selectQuery = "SELECT  * FROM " + Tablename;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Logement loge = new Logement();
               String l = cursor.getString(0);
                Gson g = new Gson();
                loge = g.fromJson(l,Logement.class);
                logementList.add(loge);
            } while (cursor.moveToNext());
        }

        return logementList;
    }
    public int getLogementCount() {
        String countQuery = "SELECT  * FROM " + Tablename;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public void deleteLogement(Logement logement) {
        Gson g = new Gson();
        String j = g.toJson(logement);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tablename, LogementContenu + " = ?",
                new String[] { String.valueOf(j) });
        db.close();

    }
}
