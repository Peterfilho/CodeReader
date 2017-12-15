package com.example.peterson.codereader2.model;

import java.io.Serializable;

/**
 * Created by peterson on 21/11/17.
 */

public class Grupo implements Serializable{

    private long id;
    private String nome;

    public Grupo() {
    }

    public Grupo(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
