package com.example.appfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class FragmentMenu extends Fragment {
    private TextView mostrarid;
    private TextView mostrarusuario;
    private Button btndeletarconta;
    private int id;
    private String usuario;

    public FragmentMenu(int id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mostrarid = (TextView) view.findViewById(R.id.mostrarid);
        mostrarusuario = (TextView) view.findViewById(R.id.mostrarusuario);
        btndeletarconta = (Button) view.findViewById(R.id.btnDeletarConta);
        mostrarid.setText("ID: "+this.id);
        mostrarusuario.setText("Usu: "+this.usuario);
        AppDataBase AppDataBase = new AppDataBase(getContext());
        btndeletarconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   AppDataBase.delete("pessoa",id,"_id=?");
                   AppDataBase.delete("nota",id,"idpessoa");
               Intent intent = new Intent(getContext(),MainActivity.class);
               startActivity(intent);
            }
        });
        return view;
    }
}