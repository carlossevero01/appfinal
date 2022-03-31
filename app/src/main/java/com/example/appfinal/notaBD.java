package com.example.appfinal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class notaBD extends SQLiteOpenHelper {

    private static final String TAG ="sql";
    private static final String NOME_BANCO = "nota.sqlite";
    private static final int VERSAO = 1;
    public notaBD(Context context) {
        super(context,NOME_BANCO, null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(TAG,"criando a tabela nota");
        sqLiteDatabase.execSQL("create table if not exists nota (_id integer primary key autoincrement, idpessoa integer, nota text );");
        Log.d(TAG,"Tabela Criada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long save(nota nota) {
        long id = nota.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("idpessoa", nota.getIdpessoa());
            values.put("nota", nota.getNota());

            if (id != 0) {
                String _id = String.valueOf(nota.getId());
                String[] whereArgs = new String[]{_id};
                int count = db.update("nota", values, "_id=?", whereArgs);
                return count;
            } else {
                id = db.insert("nota", "", values);
                return id;
            }


        } finally {
            db.close();
        }
    }


    public int delete(nota nota){
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete("nota","_id=?",new String[]{String.valueOf(nota.getId())});
            Log.d(TAG,"Deletou ["+ count + "] registro.");
            return count;
        } finally {
            db.close();
        }
    }

    public List<nota> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor c = db.query("nota",null,null,null,null,null,null,null);
            return toList(c);
        }finally {
            db.close();
        }
    }

    @SuppressLint("Range")
    private List<nota> toList(Cursor c) {
        List<nota> notas = new ArrayList<nota>();
        if(c.moveToFirst()){
            do{
                nota n = new nota();
                notas.add(n);
                n.setId(c.getInt(c.getColumnIndex("_id")));
                n.setIdpessoa(c.getInt(c.getColumnIndex("idpessoa")));
                n.setNota(c.getString(c.getColumnIndex("nota")));


            }while(c.moveToNext());
        }
        return notas;
    }
}