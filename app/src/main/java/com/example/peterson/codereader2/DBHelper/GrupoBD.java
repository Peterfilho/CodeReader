package com.example.peterson.codereader2.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterson on 21/11/17.
 */

public class GrupoBD extends SQLiteBase {

    public GrupoBD(Context context) {
        super(context);
    }

    // salvar

    public void salvarGrupo(Grupo grupo){
        ContentValues values = new ContentValues();

        values.put("nome", grupo.getNome());

        getWritableDatabase().insert("grupo", null, values);
    }

    // mostrar

    public ArrayList<Grupo> getLista(){
        String [] columns = {"id", "nome"};
        Cursor cursor = getWritableDatabase().query("grupo", columns, null,null,null,null,null,null);
        ArrayList<Grupo> grupos = new ArrayList<Grupo>();

        while(cursor.moveToNext()){
            Grupo grupo = new Grupo();
            grupo.setId(cursor.getLong(0));
            grupo.setNome(cursor.getString(1));

            grupos.add(grupo);

        }
        return grupos;
    }

    // alterar

    public void alterarGrupo(Grupo grupo){
        ContentValues values = new ContentValues();

        values.put("nome", grupo.getNome());

        String [] args = {String.valueOf(grupo.getId())};

        getWritableDatabase().update("grupo", values, "id=?", args);
    }

    public void deletaGrupo(Grupo grupo){

        String [] args = {String.valueOf(grupo.getId())};

        getWritableDatabase().delete("grupo", "id=?", args);
    }


    public Grupo find(long id) {
        Grupo result = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("grupo", new String[] { "id", "nome" }, "id=?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            result = new Grupo(cursor.getLong(0), cursor.getString(1));
        }

        db.close();

        return result;
    }

    public Grupo findByName(String name) {
        Grupo result = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("grupo", new String[] { "id", "nome" }, "nome=?",
                new String[] { String.valueOf(name) }, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            result = new Grupo(cursor.getInt(0), cursor.getString(1));
        }

        db.close();

        return result;
    }

    public List<Grupo> all() {
        List<Grupo> result = new ArrayList<Grupo>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM grupo", null);

        if (cursor.moveToFirst()) {
            do {
                result.add(new Grupo(cursor.getLong(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        db.close();

        return result;
    }

}
