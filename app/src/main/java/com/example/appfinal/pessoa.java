package com.example.appfinal;

import java.io.Serializable;
import java.util.ArrayList;

public class pessoa implements Serializable {
    private int id;
    private String nome;
    private String usuario;
    private String senha;

    public pessoa(){
        this.id=0;
        this.nome="";
        this.usuario="";
        this.senha="";

    }
    public int getId() {  return id;}
    public void setId(int id) { this.id = id;}
    public String getNome() { return nome;}
    public void setNome(String nome) { this.nome = nome;}
    public String getUsuario() { return usuario;}
    public void setUsuario(String usuario) { this.usuario = usuario;}
    public String getSenha() { return senha;}
    public void setSenha(String senha) { this.senha = senha;}

   }
