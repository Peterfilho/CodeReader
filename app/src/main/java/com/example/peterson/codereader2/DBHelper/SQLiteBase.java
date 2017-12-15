package com.example.peterson.codereader2.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by peterson on 04/12/17.
 */

public class SQLiteBase extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "CodeReader";
    private static final int DATABASE_VERSION = 2;
    protected static Context ctx;

    public SQLiteBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE grupo (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nome TEXT NOT NULL);");

        db.execSQL("CREATE TABLE codigo (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "codigo TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "descricao TEXT," +
                "id_grupo INTEGER NOT NULL," +
                "FOREIGN KEY (id_grupo) REFERENCES grupo(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("PDMLog", "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS grupo");
        db.execSQL("DROP TABLE IF EXISTS codigo");
        onCreate(db);
    }
}
