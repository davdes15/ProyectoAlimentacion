package com.example.davdes.proyectoalimentacion;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    private static final String DBNAME = "alimentos.db";
    private static final int DATABASE_VERSION = 1;
    //String SQLC = "create table if not exists usuarios(codigo integer primary key, nombre varchar(30))";
    //String SQLU = "drop table if exists usuarios";

    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }
}