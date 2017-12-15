package com.example.peterson.codereader2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.peterson.codereader2.R;
import com.example.peterson.codereader2.model.Codigo;
import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;

/**
 * Created by peterson on 08/12/17.
 */

public class CodigoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Codigo> codigos;

    public CodigoAdapter(ArrayList<Codigo> codigos, LayoutInflater inflater){
        this.inflater = inflater;
        this.codigos = codigos;
    }

    public void add(Codigo c){
        codigos.add(c);
        notifyDataSetChanged();
    }

    public void remove(int index){
        codigos.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return codigos.size();
    }

    @Override
    public Object getItem(int i) {
        return codigos.get(i);
    }


    @Override
    public long getItemId(int i) {
        return codigos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Codigo codigo = codigos.get(i);

        View v = inflater.inflate(R.layout.adapter_codigo_layout, null);

        ((TextView)v.findViewById(R.id.adapter_nome)).setText(codigo.getDescricao());
        ((TextView)v.findViewById(R.id.adapterCode)).setText(codigo.getCodigo());
        ((TextView)v.findViewById(R.id.adapterType)).setText(codigo.getTipo());


        return v;

    }
}
