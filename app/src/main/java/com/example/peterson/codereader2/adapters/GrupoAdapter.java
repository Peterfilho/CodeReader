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
import com.example.peterson.codereader2.model.Grupo;

import java.util.ArrayList;

/**
 * Created by peterson on 08/12/17.
 */

public class GrupoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Grupo> grupos;

    public GrupoAdapter(ArrayList<Grupo> grupos, LayoutInflater inflater){
        this.inflater = inflater;
        this.grupos = grupos;
    }

    public void add(Grupo b){
        grupos.add(b);
        notifyDataSetChanged();
    }

    public void remove(int index){
        grupos.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return grupos.size();
    }

    @Override
    public Object getItem(int i) {
        return grupos.get(i);
    }


    @Override
    public long getItemId(int i) {
        return grupos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Grupo grupo = grupos.get(i);

        View v = inflater.inflate(R.layout.adapter_grupo_layout, null);

        ((TextView)v.findViewById(R.id.adapter_nome)).setText(grupo.getNome());

        return v;

    }
}
