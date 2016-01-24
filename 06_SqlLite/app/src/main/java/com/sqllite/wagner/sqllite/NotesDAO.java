package com.sqllite.wagner.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsavel por prover a manipulação dos dados com a sua base utilizando SQLite.
 */
public class NotesDAO {
    /*
        Define as variáveis que armazenaram a instância da base de dados, as colunas do banco e a subclasse
        de SQLiteOpenHelper utilizada para fazer o acesso aos dados.
     */
    private SQLiteDatabase database;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID, CustomSQLiteOpenHelper.COLUMN_NOTES};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;

    /*
        Construtor da classe, recebe o contexto que está sendo usado para a abertura.
     */
    public NotesDAO(Context context){
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    //Inicia a conexão com a base de dados.
    public void open() throws SQLException {
        database = sqLiteOpenHelper.getWritableDatabase();
    }

    //Encerra a conexão com a base de dados.
    public void close() throws SQLException{
        sqLiteOpenHelper.close();
    }

    //Armazena uma nova nota criada na base de dados.
    public Note create(String note){
        //Cria um objeto ContentValues para poder inserir o conteúdo a ser cadastrado na base de dados.
        ContentValues values = new ContentValues();

        /*
            O método put recebe como argumentos o nome da coluna (chave) e o valor a ser inserido,
            no caso o conteúdo da nota.
         */
        values.put(CustomSQLiteOpenHelper.COLUMN_NOTES, note);

        /*
            Insere o conteúdo armazenado no objeto ContentValues na base de dados. O método insert, retorna
            o número da linha em que inserção foi realizada. Ele recebe com argumentos, o nome da
            tabela em que os tados serão inseridos, um valor opcional caso não seja possível inserir os dados
            na tabela (quando as colunas definidas em values não são encontradas ou a tabela não existe) e os
            valores a serem inseridos.
         */
        long insertId = database.insert(CustomSQLiteOpenHelper.TABLE_NOTES, null, values);

        /*
            Realiza uma pesquisa da base de dados e retorna um objeto Cursor com os resultados da pesquisa.
            O método query recebe como argumentos:
                O nome da tabela a ser pesquisada;
                As colunas da tabela;
                O critério a ser utilizado para a busca da query, no exemplo abaixo, o critério utilizado foi o id
                da nota.;
                Os argumentos do whery caso seja realizado separado (no exemplo os argumentos foram utilizados juntos
                por isso o valor passado foi null);
                GroupBy, Having, OrderBy caso necessário (no exemplo todos setados como null).
         */
        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES, columns, CustomSQLiteOpenHelper.COLUMN_ID + " = "
        + insertId, null, null, null, null);

        //Move o cursor da busca para a primeira posição.
        cursor.moveToFirst();

        //Preenche os dados da nota e retorna o conteúdo da mesma depois de salva na base de dados.
        Note newNote = new Note();
        newNote.setId(cursor.getLong(0));
        newNote.setNote(cursor.getString(1));
        cursor.close();
        return newNote;
    }

    //Remove uma nota da base de dados.
    public void delete(Note note){
        long id = note.getId(); //Obtém o id da nota.

        /*
            Apaga a nota da base de dados. O método delete recebe os seguintes argumentos.
                O nome da tabela a ter os dados excluídos.
                O critério a ser utilizado para apagar os dados, no caso o id da tabela.
                Os argumentos do critério, caso sejam passados separados.
         */
        database.delete(CustomSQLiteOpenHelper.TABLE_NOTES, CustomSQLiteOpenHelper.COLUMN_ID
                + " = " + id, null);
    }

    //Obtém todas as notas cadastradas na base de dados.
    public List<Note> getAll(){
        List<Note> notes = new ArrayList<Note>();

        /*
            Obtém todos os dados cadastrados na tabela. Como serão todos os dados armazenados, os argumentos passados
            são o nome da tabela e o nome das colunas, o restante dos argumentos podem ser passados como null.
         */
        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES, columns, null, null, null, null, null);

        //Move o cursor para a primeira posição e percorre o objeto preenchendo os dados da nota, enquanto houver registros.
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Note note = new Note();
            note.setId(cursor.getLong(0));
            note.setNote(cursor.getString(1));
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return  notes;
    }
}//class NotesDAO
