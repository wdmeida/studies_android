package com.sqllite.wagner.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Para utilizar o SQLite, é necessário criar uma subclasse de SQLiteOpenHelper.
 * Em seguida é necessário sobrescrever os métodos OnCreate() e OnUpgrade(). O primeiro
 * é chamado quando ainda não existe um banco de dados, nele você deve incluir os comandos
 * para criar tabelas e inicializar qualquer tipo de dados, se preciso. O segundo é chamado
 * quando a versão da base de dados é alterada, e nele você deve incluir quaisquer comandos
 * relacionadoas à alteração do esquema, como alterações em tabelas e colunas.
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    //Define constantes com os nome da tabela a ser criara, a coluna id e a coluna note.
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTES = "note";

    //Define o nome da base da dados e a sua versão.
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    //Comando SQL responsável por criar a base da dados.
    private static final String DATABASE_CREATE = "create table " + TABLE_NOTES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NOTES + " text not null);";

    public CustomSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
        O método onCreate é chamado quando ainda não existe um banco da dados, nele você
        deve incluir os comandos para criar tabelas e inicializar qualquer tipo de dados, se preciso.
     */
    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }






















}//class CustomSQLiteOpenHelper
