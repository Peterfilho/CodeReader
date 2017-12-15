package com.example.peterson.codereader2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peterson.codereader2.DBHelper.GrupoBD;
import com.example.peterson.codereader2.R;
import com.example.peterson.codereader2.model.Grupo;
import com.example.peterson.codereader2.services.NewGroupService;

/**
 * Created by peterson on 11/11/17.
 */

public class NovoGrupo extends MainActivity {

    EditText editTextGroupName;
    Button buttonCadNewGroup;
    Grupo editaGrupo, grupo;
    GrupoBD dbHelper;
    private String nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_grupo);
        returnMain();


        grupo = new Grupo();
        dbHelper = new GrupoBD(NovoGrupo.this);

        Intent intent = getIntent();

        editaGrupo = (Grupo) intent.getSerializableExtra("grupo-escolhido");

        editTextGroupName = (EditText) findViewById(R.id.editTextGroupName);

        buttonCadNewGroup = (Button) findViewById(R.id.buttonCadNewGroup);

        if(editaGrupo != null){
            buttonCadNewGroup.setText("Alterar");

            editTextGroupName.setText(editaGrupo.getNome());

            grupo.setId(editaGrupo.getId());

        }else{
            buttonCadNewGroup.setText("Cadastrar");
        }

        buttonCadNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grupo.setNome(editTextGroupName.getText().toString());

                Object grupoExiste = dbHelper.findByName(grupo.getNome());
                    if(camposEstaoVazios(editTextGroupName)) {
                        mostrarMensagemComCamposVazios();
                    } else if(grupoExiste == null) {

                        if(buttonCadNewGroup.getText().toString().equals("Cadastrar")) {
                            dbHelper.salvarGrupo(grupo);
                            initService();
                            dbHelper.close();
                        } else {
                            dbHelper.alterarGrupo(grupo);
                            Toast toast = Toast.makeText(getApplicationContext(), "Grupo atualizado com sucesso!", Toast.LENGTH_LONG);
                            toast.show();
                            dbHelper.close();
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        mostrarMensagem("Grupo já existe, cadastre outro nome!");
                    }
            }
        });
    }

    private void initService() {
        Intent serviceIntent = new Intent(this, NewGroupService.class);
        startService(serviceIntent);
    }



}
