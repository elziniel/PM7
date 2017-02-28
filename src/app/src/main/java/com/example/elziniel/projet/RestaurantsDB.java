package com.example.elziniel.projet;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Elziniel on 01/01/2016.
 */
public class RestaurantsDB {
    public static final String ID = "_id";
    public static final String NOM = "nom";
    public static final String ADRESSE = "adresse";
    public static final String TELEPHONE = "telephone";
    public static final String SITE = "site";
    public static final String NOTE = "note";
    public static final String PRIX = "prix";
    public static final String CUISINE = "cuisine";

    public static final String TABLE_NAME = "restaurants";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM + " TEXT NOT NULL, " +
            ADRESSE + " TEXT NOT NULL, " +
            TELEPHONE + " TEXT NOT NULL, " +
            SITE + " TEXT NOT NULL, " +
            NOTE + " TEXT, " +
            PRIX + " TEXT, " +
            CUISINE + " TEXT);";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
