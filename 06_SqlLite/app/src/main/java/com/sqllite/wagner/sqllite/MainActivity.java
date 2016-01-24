package com.sqllite.wagner.sqllite;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends ListActivity {

    private NotesDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dao = new NotesDAO(this);
        try {
            dao.open();
        }catch (SQLException e) {
            dao = null;
        }
    }

    @Override
    protected void onResume(){
        try {
            dao.open();
        }catch (SQLException e) {
            dao = null;
            return;
        }

        List<Note> notes = dao.getAll();
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this,android.R.layout.simple_list_item_1,notes);
        setListAdapter(adapter);
        super.onResume();
    }

    @Override
    protected void onPause(){
        try {
            dao.close();
        }catch (SQLException e) {
            dao = null;
            return;
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == R.id.add_note){
           Intent intent = new Intent(this, AddNoteActivity.class);
           startActivity(intent);
       }
      return super.onOptionsItemSelected(item);
    }




























}
