package com.sharedprefs.wagner.sharedprefs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final static String APP_PREFS = "app_prefs";
    final static String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Seta o layout que sera utilizado
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume(){
        super.onResume();

        /*
        SharedPreferences é um tipo de armazenamento que utiliza os pares
        chave/valor indicado para configurações e dados isolados.
        */

        /*  Obtém o SharedPreferences através do método getSharedPreferences que recebe
            como argumentos, o nome, e o modo em que esteee sera obtido.
        */
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);

        //Obtém o dados desejado através do nome, o segundo parâmetro é o valor padrão caso este não seja encontrado.
        String username = prefs.getString(USERNAME_KEY, null);

        //Obtém os componentes do view.
        TextView message = (TextView) findViewById(R.id.welcome_message);
        Button addNameButton = (Button) findViewById(R.id.add_name_button);

        if(username != null){
            message.setText("Bem vindo, " + username + "!");
            addNameButton.setText("Trocar de nome");
        }else{
            message.setText("Você não cadastrou seu nome...");
            addNameButton.setText("Adicionar nome");
        }

        //Registra a ação a ser executada pelo evento, e realiza a chamada a nova Activity.
        addNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNameActivity.class);
                startActivity(intent);
            }
        });















    }
}
