package com.example.peterson.codereader2.model;

import java.io.Serializable;

/**
 * Created by peterson on 21/11/17.
 */

public class Codigo implements Serializable{

    private long id;
    private String codigo;
    private String tipo;
    private String descricao;
    private long idGrupo;

    public Codigo() {
    }

    public Codigo(String codigo, String tipo, String descricao, long idGrupo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.descricao = descricao;
        this.idGrupo = idGrupo;
    }

    public Codigo(long id, String codigo, String tipo, String descricao, long idGrupo) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.descricao = descricao;
        this.idGrupo = idGrupo;
    }

    public long getId_grupo() {
        return idGrupo;
    }

    public void setId_grupo(long id_grupo) {
        this.idGrupo = id_grupo;
    }

    @Override
    public String toString() {
        return codigo.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
