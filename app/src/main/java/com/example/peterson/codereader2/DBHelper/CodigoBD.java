package com.example.peterson.codereader2.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.peterson.codereader2.model.Codigo;
import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by peterson on 21/11/17.
 */

public class CodigoBD extends SQLiteBase {

    public CodigoBD(Context context) { super(context); }

    //save

    public void salvarCodigo(Codigo codigo){
        ContentValues values = new ContentValues();

        values.put("codigo", codigo.getCodigo());
        values.put("tipo", codigo.getTipo());
        values.put("descricao", codigo.getDescricao());
        values.put("id_grupo", codigo.getId_grupo());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("codigo", null, values);

        db.close();
    }

    //update

    public void alterarCodigo(Codigo codigo){
        ContentValues values = new ContentValues();

        values.put("codigo", codigo.getCodigo());
        values.put("tipo", codigo.getTipo());
        values.put("descricao", codigo.getDescricao());
        values.put("id_grupo", codigo.getId_grupo());

        String [] args = {String.valueOf(codigo.getId())};
        getWritableDatabase().update("codigo", values, "id=?", args);
    }

    //delete

    public void deletarCodigo (Codigo codigo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("codigo", "id = ?",
                new String[] { String.valueOf(codigo.getId()) });
        db.close();
    }


    //list
    public ArrayList<Codigo> getLista(){
        String [] columns = {"id", "codigo", "tipo", "descricao"};
        Cursor cursor = getWritableDatabase().query("codigo", columns , null, null, null, null, null, null);
        ArrayList<Codigo> codigos = new ArrayList<Codigo>();

        while (cursor.moveToNext()){
            Codigo codigo = new Codigo();

            codigo.setId(cursor.getLong(0));
            codigo.setCodigo(cursor.getString(1));
            codigo.setTipo(cursor.getString(2));
            codigo.setDescricao(cursor.getString(3));

            codigos.add(codigo);
        }
        return codigos;
    }


    public ArrayList<Codigo> all() {
        ArrayList<Codigo> result = new ArrayList<Codigo>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM codigo", null);

        if (cursor.moveToFirst()) {
            do {
                result.add(new Codigo(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());
        }

        db.close();

        return result;
    }


    public ArrayList<Codigo> findAllByGroup(Long idGroup) {
        ArrayList<Codigo> result = new ArrayList<Codigo>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("codigo", new String[] { "id", "codigo", "tipo", "descricao", "id_grupo" }, "id_grupo=?",
                new String[] { String.valueOf(idGroup) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                result.add(new Codigo(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());
        }

        db.close();

        return result;
    }
}
