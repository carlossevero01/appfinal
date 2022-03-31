package com.example.appfinal;

import java.io.Serializable;

public class nota implements Serializable {
    private int id;
    private int idpessoa;
    private String nota;
    public nota(){
        this.id=0;
        this.idpessoa=0;
        this.nota="";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(int idpessoa) {
        this.idpessoa = idpessoa;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
