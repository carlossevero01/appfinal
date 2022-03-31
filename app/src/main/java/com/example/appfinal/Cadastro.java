package com.example.appfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Cadastro extends AppCompatActivity implements View.OnClickListener
{
    EditText nome;
    EditText usuario;
    EditText senha;
    Button btncadastrar;
    pessoaBD db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome = (EditText) findViewById(R.id.editcadastronome);
        usuario = (EditText) findViewById(R.id.editcadastrousuario);
        senha = (EditText) findViewById(R.id.editcadastrosenha);
        btncadastrar = (Button) findViewById(R.id.btnCadastrar);
        btncadastrar.setOnClickListener(this);
        db = new pessoaBD(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==btncadastrar.getId())
        {
            pessoa p = new pessoa();
            p.setNome(nome.getText().toString().trim());
            p.setUsuario(usuario.getText().toString().trim());
            p.setSenha(senha.getText().toString().trim());
            List<pessoa> pessoas = db.findAll();
            for (int i = 0; i < pessoas.size(); i++)
            {
                if (usuario.getText().toString().trim().equals(pessoas.get(i).getUsuario()))
                {
                   Toast.makeText(getApplicationContext(),"Usuario já existente",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    db.save(p);
                    Toast.makeText(getApplicationContext(),"Cadastro Realizado com Sucesso",Toast.LENGTH_SHORT);
                    finish();
                }
            }
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}