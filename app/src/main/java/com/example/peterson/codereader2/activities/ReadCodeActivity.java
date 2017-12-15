package com.example.peterson.codereader2.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peterson.codereader2.DBHelper.CodigoBD;
import com.example.peterson.codereader2.DBHelper.GrupoBD;
import com.example.peterson.codereader2.R;
import com.example.peterson.codereader2.model.Codigo;
import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;

/**
 * Created by peterson on 12/11/17.
 */

public class ReadCodeActivity extends MainActivity {

    EditText editText_cod, editText_type, editText_dec;
    AutoCompleteTextView autoCompleteTextView;
    Button button_cadcode;
    Codigo editCode, codigo;
    CodigoBD dbHelper, codigodb;
    GrupoBD grupoBD;
    Grupo grupo;
    AutoCompleteTextView selectGroup;
    ArrayAdapter<Grupo> grupoAdapter;
    Long groupId;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private TextView formatTxt, contentTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readcode);
        returnMain();


        formatTxt = (EditText) findViewById(R.id.editText_type);
        contentTxt = (EditText) findViewById(R.id.editText_cod);

        grupoBD = new GrupoBD(this);
        setAutoComplete();

        dbHelper = new CodigoBD(ReadCodeActivity.this);

        Intent intent = getIntent();
        editCode = (Codigo) intent.getSerializableExtra("codigo-escolhido");

        editText_cod = (EditText) findViewById(R.id.editText_cod);
        editText_type = (EditText) findViewById(R.id.editText_type);
        editText_dec = (EditText) findViewById(R.id.editText_dec);
        //Como vai pegar id do grupo selecionado?
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteGrupo);


        button_cadcode = (Button) findViewById(R.id.button_cadcode);

        if (editCode != null) {
            button_cadcode.setText("Alterar");
            editText_cod.setText(editCode.getCodigo());
            editText_type.setText(editCode.getTipo());
            editText_dec.setText(editCode.getDescricao());
            Grupo selectGroup = grupoBD.find(editCode.getId_grupo());
            autoCompleteTextView.setText(selectGroup.getNome());
            groupId = editCode.getId_grupo();
        } else {
            button_cadcode.setText("Cadastrar");
        }

        selectGroup = (AutoCompleteTextView) findViewById(R.id.autoCompleteGrupo);
        selectGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setGroup(grupoAdapter, position);

            }
        });

        button_cadcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoTxt = editText_cod.getText().toString();
                String tipoTxt = editText_type.getText().toString();
                String descricaoTxt = editText_dec.getText().toString();


                codigo = new Codigo();


                if(!campoEstaVazio(codigoTxt) && !campoEstaVazio(tipoTxt) && !campoEstaVazio(descricaoTxt) && !campoEstaVazio(selectGroup) && groupId != null) {
                        codigo.setCodigo(codigoTxt);
                        codigo.setTipo(tipoTxt);
                        codigo.setDescricao(descricaoTxt);
                        codigo.setId_grupo(groupId);

                        Log.d("TESTE", "FD" + groupId);

                        if (button_cadcode.getText().toString().equals("Cadastrar")) {
                            dbHelper.salvarCodigo(codigo);
                        } else {
                            codigo.setId(editCode.getId());
                            dbHelper.alterarCodigo(codigo);
                            Toast toast = Toast.makeText(getApplicationContext(), "Codigo atualizado com sucesso!", Toast.LENGTH_LONG);
                            toast.show();
                            dbHelper.close();
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                } else {
                    mostrarMensagemComCamposVazios();
                }
            }
        });
    }


    protected  void setGroup(ArrayAdapter<Grupo> categoryAdapter, int position) {
        this.groupId = grupoAdapter.getItem(position).getId();
    }

//product barcode mode

    public void scanBar(View v) {

        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }



    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    //on ActivityResult method

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent

                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                editText_cod.setText(contents);
                editText_type.setText(format);

                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    protected ArrayList<Grupo> getGrupos() {
        ArrayList<Grupo> b = new ArrayList<Grupo>();

        for(Grupo grupo : grupoBD.all()){
            b.add(grupo);
        }

        return  b;
    }

    protected void setAutoComplete() {
        grupoAdapter = new ArrayAdapter<Grupo>(this,
                android.R.layout.simple_dropdown_item_1line, getGrupos());

        AutoCompleteTextView selectGrupo = (AutoCompleteTextView) findViewById(R.id.autoCompleteGrupo);

        selectGrupo.setAdapter(grupoAdapter);
        selectGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long grupoId = (long) grupoAdapter.getItem(position).getId();
                grupo = grupoBD.find(grupoId);
            }
        });


    }
}
