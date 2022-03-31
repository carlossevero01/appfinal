package com.example.appfinal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AppDataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "pessoa.sqlite";
    public static final int DB_VERSION = 1;


    public AppDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SQLiteDatabase db = getWritableDatabase();
        String tabelaCliente = "create table if not exists pessoa (_id integer primary key autoincrement, nome text, usuario text, senha text);";
        String tabelaNota = "CREATE TABLE IF NOT EXISTS nota (_id integer PRIMARY KEY AUTOINCREMENT, idpessoa integer, nota text);";
        try {
            db.execSQL(tabelaCliente);
            db.execSQL(tabelaNota);
        } catch (SQLException e) {
            Log.e("DB_LOG", "onCreate:" + e.getLocalizedMessage());
        } finally {
          //  db.close();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(String tabela, pessoa p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", p.getNome());
        values.put("usuario", p.getUsuario());
        values.put("senha", p.getSenha());
        boolean V = false;

        try {
            V = db.insert(tabela, null, values) > 0;
        } catch (SQLException e) {
            Log.e("DB_NAME", "onINSERT" + e.getLocalizedMessage());
        } finally {
         //   db.close();
        }
        return V;
    }

    public boolean insert(String tabela, nota n) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idpessoa", n.getIdpessoa());
        values.put("nota", n.getNota());
        boolean V = false;

        try {
            V = db.insert(tabela, null, values) > 0;
        } catch (SQLException e) {
            Log.e("DB_NAME", "onINSERT" + e.getLocalizedMessage());
        } finally {
         //    db.close();
        }
        return V;
    }

    @SuppressLint("Range")
    public List<pessoa> selectPessoa() {
        SQLiteDatabase db = getWritableDatabase();
        List<pessoa> retorno = new ArrayList<>();
        Cursor c;
        String sqlDeConsulta = "select * from pessoa";
        try{
            c = db.rawQuery(sqlDeConsulta, null);
            if (c.moveToFirst()) {
                do {
                    pessoa p = new pessoa();
                    p.setId(c.getInt(c.getColumnIndex("_id")));
                    p.setNome(c.getString(c.getColumnIndex("nome")));
                    p.setUsuario(c.getString(c.getColumnIndex("usuario")));
                    p.setSenha(c.getString(c.getColumnIndex("senha")));
                    retorno.add(p);
                }
                while (c.moveToNext());
            }
        }catch(SQLException e){
            Log.e("DB_NAME","onSelectPessoa"+e.getLocalizedMessage());
        }finally {
          //  db.close();
        }

        return retorno;
    }

    @SuppressLint("Range")
    public List<nota> selectNota()
    {
        SQLiteDatabase db = getWritableDatabase();
        List<nota> retorno = new ArrayList<>();
        Cursor c;
        String sqlDeConsulta = "select * from nota";
        try{
            c = db.rawQuery(sqlDeConsulta, null);
            if (c.moveToFirst())
            {
                do {
                    nota n = new nota();
                    n.setId(c.getInt(c.getColumnIndex("_id")));
                    n.setIdpessoa(c.getInt(c.getColumnIndex("idpessoa")));
                    n.setNota(c.getString(c.getColumnIndex("nota")));
                    retorno.add(n);
                }
                while (c.moveToNext());

            }
        }catch (SQLException e){
            Log.e("DB_NAME","onSelectNota"+e.getLocalizedMessage());
        }finally {
         //   db.close();
        }

        return retorno;
    }
    /*for(pessoa objpessoa: appDataBase.selectPessoa()){Log.i("DB_SELECTPESSOA","Pessoa:"+objpessoa.getId());}*/
    public boolean delete(String tabela,int id, String whereClause){
        SQLiteDatabase db = getWritableDatabase();
        boolean V=false;
        try{
            V= db.delete(tabela,whereClause,new String[]{Integer.toString(id)})>0;
        }catch (SQLException e){
            Log.e("DB_NAME","DB_onDelete"+e.getLocalizedMessage());
        }finally {
         //  db.close();
        }
        return V;
    }
    //AppDataBase.delete("pessoa",7);
    public boolean updateNota(String tabela,nota n){
        int id = n.getId();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idpessoa", n.getIdpessoa());
        values.put("nota", n.getNota());
        boolean V = false;
        try{
            V = db.update(tabela,values,"_id=?",new String[]{Integer.toString(id)})>0;
        }catch (SQLException e){
            Log.e("DB_NAME","DB_onUpdateNota"+e.getLocalizedMessage());
        }finally {
          //  db.close();
        }
        return V;
    }
    public boolean updatePessoa(String tabela,pessoa p){
        int id = p.getId();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", p.getNome());
        values.put("usuario", p.getUsuario());
        values.put("senha", p.getSenha());
        boolean V = false;
        try{
            V = db.update(tabela,values,"_id=?",new String[]{Integer.toString(id)})>0;
        }catch (SQLException e){
            Log.e("DB_NAME","DB_onUpdatePessoa"+e.getLocalizedMessage());
        }finally {
        //    db.close();
        }
        return V;
    }
            /*pessoa p = new pessoa();
            p.setId(1);
            p.setNome("carlos");
            p.setUsuario("car");
            p.setSenha("123");
            AppDataBase.updatePessoa("pessoa",p);
            finish();*/

}
