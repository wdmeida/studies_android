package com.sqllite.wagner.sqllite;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

/**
 * Created by Wagner on 24/01/2016.
 */
public class AddNoteActivity extends AppCompatActivity {
    private NotesDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        try {
            dao = new NotesDAO(this);
            dao.open();

            Button saveButton = (Button) findViewById(R.id.save_note_button);
            final EditText noteText = (EditText) findViewById(R.id.note_text);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String note = noteText.getEditableText().toString();
                    dao.create(note);
                    finish();
                }
            });
        } catch (SQLException e){
            dao = null;
            return;
        }
    }

    @Override
    protected void onResume(){
        try {
            dao.open();
            super.onResume();
        }catch (SQLException e){
            return;
        }
    }

    @Override
    protected void onPause(){
        try {
            dao.close();
            super.onPause();
        }catch (SQLException e){
            return;
        }
    }
}//class AddNoteActivity
