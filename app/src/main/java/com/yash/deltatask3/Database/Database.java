package com.yash.deltatask3.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.yash.deltatask3.Database.pokemon_contract.*;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SCORE.db";
    public static final int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " +
                PokemonEntry.TABLE_NAME + " (" +
                PokemonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PokemonEntry.COLUMN_POKEMON_NAME + " TEXT NOT NULL, " +
                PokemonEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                PokemonEntry.COLUMN_STATS + " TEXT NOT NULL, " +
                PokemonEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PokemonEntry.TABLE_NAME);
        onCreate(db);
    }
}
