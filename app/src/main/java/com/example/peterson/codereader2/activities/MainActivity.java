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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregarGrupos();

        final CodigoBD code = new CodigoBD(getApplicationContext());
        /*
        for(Codigo c : code.all()){

            Log.d("PDMLog",c.toString());
            code.deletarCodigo(c);
        }

        Codigo codigozim = new Codigo("dfd", "dffd", "df", 15);
        Codigo codigo2 = new Codigo("dfd", "dfd", "haha", 15);
        Codigo codigo3 = new Codigo("dfdf", "dfd", "ihuu", 17);
        code.salvarCodigo(codigozim);
        code.salvarCodigo(codigo2);
        code.salvarCodigo(codigo3);

*/
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
    }
/*
    protected void onResume(){
        super.onResume();
        carregarGrupos();
    }
*/
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

/*
    public void carregarGrupos(){
        dbHelper = new GrupoBD(this);
        listviewGrupos = dbHelper.getLista();
        dbHelper.close();

        if(listviewGrupos != null){
            adapterGroup = new GrupoAdapter(
                    (ArrayList<Grupo>) dbHelper.all(), getLayoutInflater());
            lista.setAdapter(adapter);
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendStatusIdWhenChangeActivity(adapter, position);
            }
        });

    }
*/
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

    protected void notificarNovoGrupo() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_info_details);
        mBuilder.setContentTitle("Novo grupo criado!");
        mBuilder.setContentText("Você criou novo grupo, cadastre codigos nele!");

        //ao clicar na notificação ele tira o icone
        mBuilder.setAutoCancel(true);
        //barra de progresso. Se verdadeiro com tempo indefinido, se falso com tempo na metade
        //mBuilder.setProgress(100, 50, true);
        //define a cor da luz que pisca da notificação
        mBuilder.setLights(Color.GREEN, 500, 500);
        //define para o toque da notificação ser o toque padrao do celular
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        //som padrao de alarme
        mBuilder.setSound(alarmSound);
        //notificação acontecendo. Nesse caso não pode fechar a notificação (PS: desabilitar auto cancel)
        //mBuilder.setOngoing(true);
        //passa a classe que quer que seja aberta quando clica
        Intent resultIntent = new Intent(this, ReadCodeActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //cria uma notificação e um id
        mNotificationManager.notify(1234, mBuilder.build());
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
