package com.example.peterson.codereader2.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.peterson.codereader2.DBHelper.CodigoBD;
import com.example.peterson.codereader2.DBHelper.GrupoBD;
import com.example.peterson.codereader2.R;
import com.example.peterson.codereader2.adapters.GrupoAdapter;
import com.example.peterson.codereader2.model.Codigo;
import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;

/**
 * Created by peterson on 07/11/17.
 */

public class MainActivity extends AppCompatActivity {

    ListView lista;
    GrupoBD dbHelper;
    ArrayList<Grupo> listviewGrupos;
    Grupo grupo;
    ArrayAdapter adapter;
    ListAdapter adapterGroup;
    ListView listaGrupo;
    ImageButton btnShare;
    Intent shareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregarGrupos();

        final CodigoBD code = new CodigoBD(getApplicationContext());

        Button btnLerCod = (Button) findViewById(R.id.NewCode);
        btnLerCod.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReadCodeActivity.class);
                startActivity(intent);
            }
        });

        Button btnNewGroup = (Button) findViewById(R.id.buttonnewgroup);
        btnNewGroup.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NovoGrupo.class);
                startActivity(intent);
            }
        });

        listaGrupo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                grupo = (Grupo) adapter.getItemAtPosition(i);
                return false;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final MenuItem menuDelete = menu.add("Excluir grupo");
        final MenuItem menuUpdate = menu.add("Editar grupo");
        final MenuItem menuShare = menu.add("Compartilhar Grupo");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                dbHelper = new GrupoBD(MainActivity.this);
                dbHelper.deletaGrupo(grupo);
                dbHelper.close();
                carregarGrupos();
                return true;
            }
        });

        menuUpdate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                listaGrupo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                        Grupo grupoEscolhido = (Grupo) adapter.getItemAtPosition(i);

                        Intent intent = new Intent(MainActivity.this, NovoGrupo.class);
                        intent.putExtra("grupo-escolhido", grupoEscolhido);
                        startActivity(intent);
                        return false;
                    }
                });
                return true;
            }
        });

        menuShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                listaGrupo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {

                        CodigoBD codigo = new CodigoBD(MainActivity.this);

                        ArrayList<Codigo> lista = codigo.findAllByGroup(l);

                        shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        //String texto = "Olá sou um texto compartilhado";

                        shareIntent.putExtra(Intent.EXTRA_TEXT, lista.toString());
                        shareIntent.setType("text/plain");
                        startActivity(shareIntent);

                        return true;
                    }
                });
                return false;
            }
        });
    }

    protected void returnMain() {
        ImageButton returnMain = (ImageButton) findViewById(R.id.returnMain);
        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void carregarGrupos() {
        listaGrupo = (ListView) findViewById(R.id.listviewgrupos);
        dbHelper = new GrupoBD(this);

        adapterGroup = new GrupoAdapter(
                (ArrayList<Grupo>) dbHelper.all(), getLayoutInflater());
        listaGrupo.setAdapter(adapterGroup);

        listaGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendStatusIdWhenChangeActivity(adapterGroup, position);
            }



        });
        registerForContextMenu(listaGrupo);
    }


    protected boolean camposEstaoVazios(EditText text) {
        return text.getText().toString().trim().equals("") || text.getText().toString().trim().equals("");
    }

    protected boolean campoEstaVazio(Object text) {
        return text.toString().trim().equals("") |text.toString().trim().equals("");
    }

    protected void mostrarMensagem(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void mostrarMensagemComCamposVazios() {
        mostrarMensagem("Os campos não podem ser vazios!");
    }

    protected boolean fieldIsEmpty(EditText text) {
        return text.getText().toString().trim().equals("") || text.getText().toString().trim().equals("");
    }

    protected void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void showMessageWhenFieldsEmpty() {
        showMessage("Os campos não podem estar vazios");
    }

    private void sendStatusIdWhenChangeActivity(ListAdapter grupoAdapter, int position) {
        Bundle params = new Bundle();
        params.putLong("id", grupoAdapter.getItemId(position));
        Intent intent = new Intent(getBaseContext(), ListaCodigos.class);
        intent.putExtras(params);
        startActivity(intent);
    }


}
