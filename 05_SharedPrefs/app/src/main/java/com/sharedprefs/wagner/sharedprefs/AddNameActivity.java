package com.sharedprefs.wagner.sharedprefs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Wagner on 14/01/2016.
 */
public class AddNameActivity extends AppCompatActivity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_name);

        prefs = getSharedPreferences(MainActivity.APP_PREFS, MODE_PRIVATE);

        final EditText name = (EditText) findViewById(R.id.name_edit_text);
        Button saveButton = (Button) findViewById(R.id.add_name_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtém o nome do componente TextView
                String username = name.getEditableText().toString();
                SharedPreferences.Editor editor = prefs.edit();

                //Seta o valor do TextView no SharedPreferences para que seja salvo.
                editor.putString(MainActivity.USERNAME_KEY, username);
                //Envia as alterações e finaliza a mesma.
                editor.commit();
                finish();
            }
        });
    }
}
