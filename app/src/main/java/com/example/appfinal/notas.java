package com.example.appfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
    private ArrayList<String> lista;
    private notaBD ndb;
    private pessoaBD db;

    private Boolean on=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
      //  List<nota> not = (List<nota>) ndb.findAll();
      //  ndb = new notaBD(this);



        String nome = getIntent().getStringExtra("nome");

        bvnome = (TextView) findViewById(R.id.notasbemvindo);
        bvnome.setText("Bem vindo "+nome);
        listView = (ListView) findViewById(R.id.listView);
        novaanot = (EditText) findViewById(R.id.adcanot);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);

        lista = new ArrayList<>();


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,lista);


        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==btnAdicionar.getId()) {
                    lista.add(novaanot.getText().toString().trim());
                    novaanot.setText("");
                    adapter.notifyDataSetChanged();
                }
                }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lista.set(i,novaanot.getText().toString().trim());
                listView.setAdapter(adapter);
                novaanot.setText("");
            }
        });
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