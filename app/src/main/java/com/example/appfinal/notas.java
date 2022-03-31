package com.example.appfinal;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class notas extends AppCompatActivity {
    private ListView listView;
    private TextView bvnome;
    private EditText novaanot;
    private Button btnAdicionar;
    private Button btnSalvar;
    private AppDataBase AppDataBase ;
    private ArrayList<String> notas;

    private Bundle savedInstanceState;
    private Bundle args;
    private Boolean on=false;
    private int idpessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        AppDataBase = new AppDataBase(this);

        args = getIntent().getExtras();

        String nome = args.getString("nome");
        idpessoa = args.getInt("id");
        System.out.println(idpessoa);
        bvnome = (TextView) findViewById(R.id.notasbemvindo);
        bvnome.setText("Bem vindo "+nome);
        listView = (ListView) findViewById(R.id.listView);
        novaanot = (EditText) findViewById(R.id.adcanot);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);


        notas = new ArrayList<>();
        for(nota objnota : AppDataBase.selectNota()){
            if(objnota.getIdpessoa()==idpessoa){
                notas.add(objnota.getNota());
            }
        }
        if(notas.isEmpty())
        {
            nota n = new nota();
            n.setIdpessoa(idpessoa);
            n.setNota("primeira nota");
            AppDataBase.insert("nota",n);
            notas.add("primeira nota");
        }




        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,notas);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notas.set(i,novaanot.getText().toString().trim());
                novaanot.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==btnSalvar.getId())
                {
                    for(nota objnota : AppDataBase.selectNota())
                    {
                        if(objnota.getIdpessoa()==idpessoa)
                        {   AppDataBase.delete("nota",idpessoa,"idpessoa=?");
                            for(String nota: notas)
                            {
                                nota n = new nota();
                                n.setIdpessoa(idpessoa);
                                n.setNota(nota);

                                AppDataBase.insert("nota",n);
                            }
                        }
                    }
                }
            }
        });
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==btnAdicionar.getId())
                {   boolean esc=false;
                    notas.add(novaanot.getText().toString().trim());
                    novaanot.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        FragmentMenu frag = (FragmentMenu) new FragmentMenu();
        if(id == R.id.menuexpansive){
            if(!on){
               getSupportFragmentManager().beginTransaction()
                       .add(R.id.container,frag).commit();
                        on=true;
            }else{
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                on=false;
            }


            Toast.makeText(getApplicationContext(),"Menu",Toast.LENGTH_SHORT).show();

            return true;
        }
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super. onOptionsItemSelected(item);
    }
}