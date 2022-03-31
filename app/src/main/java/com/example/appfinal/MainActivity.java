package com.example.appfinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnCadastro;
    Button btnLogin;
    EditText usuario;
    EditText senha;
    AppDataBase AppDataBase;
    TextView conselho;
    Bundle args= new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDataBase = new AppDataBase(this);

        btnCadastro = (Button) findViewById(R.id.btnCadastro);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        usuario = (EditText) findViewById(R.id.editUsuario);
        senha = (EditText) findViewById(R.id.editSenha);
        btnCadastro.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        conselho = (TextView) findViewById(R.id.conselho);
        String url = "https://api.adviceslip.com/advice";
        executar t = new executar();
        t.execute(url);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnCadastro.getId()){
            Intent intent = new Intent(this,Cadastro.class);
            startActivity(intent);
        }

        if(view.getId()==btnLogin.getId())
        {
            Intent intent = new Intent(this,notas.class);
            Boolean verifica=false;
            List<pessoa> pessoas = AppDataBase.selectPessoa();
            for(pessoa objpessoa: pessoas) {
                if (usuario.getText().toString().trim().equals(objpessoa.getUsuario())&&senha.getText().toString().trim().equals(objpessoa.getSenha())) {
                    verifica=true;
                    args.putInt("id",objpessoa.getId());
                    args.putString("nome",objpessoa.getNome());
                    args.putString("usuario",objpessoa.getUsuario());
                    intent.putExtras(args);
                    startActivity(intent);
                }
            }
            if(verifica==false){
                Toast.makeText(getApplicationContext(),"Usuario ou senha incorreto",Toast.LENGTH_SHORT).show();

            }
        }

    }

    class executar extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer = new StringBuffer();
            try{
                URL url2 = new URL(strings[0]);
                HttpsURLConnection conexao = (HttpsURLConnection) url2.openConnection();
                InputStream input = conexao.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader leitura = new BufferedReader(reader);
                String aux;
                if((aux= leitura.readLine())!=null){
                    buffer.append(aux);
                }
                System.out.println(buffer.toString());
            }   catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);

            JSONObject s = null;
            JSONObject r = null;
            try{
                s = new JSONObject(p.toString());
                r = s.getJSONObject("slip");
                conselho.setText("Id : "+r.getString("id")+" "+r.getString("advice"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}