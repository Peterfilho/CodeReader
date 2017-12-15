package com.example.peterson.codereader2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.peterson.codereader2.DBHelper.CodigoBD;
import com.example.peterson.codereader2.R;
import com.example.peterson.codereader2.adapters.CodigoAdapter;
import com.example.peterson.codereader2.model.Codigo;
import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;

/**
 * Created by peterson on 19/11/17.
 */

public class ListaCodigos extends MainActivity {

    ListView lista;
    CodigoBD dbHelper;
    ListView listviewCodigos;
    Codigo codigo;
    ArrayAdapter adapter;
    CodigoAdapter codigoAdapter;
    Long idGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_codigos);
        returnMain();

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        if(params!=null){
            Long id = params.getLong("id");
            Log.d("TESTE", " ID " + id);
            idGrupo = id;
        }

        carregarCodigos();
        registerForContextMenu(listviewCodigos);

        listviewCodigos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                codigo = (Codigo) adapter.getItemAtPosition(i);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final MenuItem menuDelete = menu.add("Excluir codigo");
        final MenuItem menuUpdate = menu.add("Editar codigo");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                dbHelper = new CodigoBD(ListaCodigos.this);
                dbHelper.deletarCodigo(codigo);
                carregarCodigos();
                return true;
            }
        });

        menuUpdate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                listviewCodigos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                        Codigo codigoEscolhido = (Codigo) adapter.getItemAtPosition(i);

                        Intent intent = new Intent(ListaCodigos.this, ReadCodeActivity.class);
                        intent.putExtra("codigo-escolhido", codigoEscolhido);
                        startActivity(intent);
                        return false;
                    }
                });
                return true;
            }
        });
    }



    private void carregarCodigos() {
        listviewCodigos = (ListView) findViewById(R.id.listviewcodigos);

        dbHelper = new CodigoBD(this);
        codigoAdapter = new CodigoAdapter(dbHelper.findAllByGroup(idGrupo), getLayoutInflater());

        listviewCodigos.setAdapter(codigoAdapter);
    }


}
