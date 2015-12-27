package com.layouts.wagner.layouts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Seta a activity que será exibida ao usuário.
        setContentView(R.layout.linear);

        //Obtém a referência do widget (componente) para poder pegar o conteúdo inserido pelo usuário.
        final EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);

        //Obtém a referência do botão que será clicado pelo usuário para disparar a ação.
        final Button seeMessageButton = (Button) findViewById(R.id.see_message_button);

        //Obtém a referência da área onde será exibido o conteúdo do usuário.
        final TextView showMessageText = (TextView) findViewById(R.id.show_message_text);

        //Configura o listener do evento, a ser disparado quando o botão for clicado.
        seeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtém o nome setado na caixa de texto
                String name = nameEditText.getEditableText().toString();
                //Substitui a expressão regular na mensagem de saudação pelo nome digitado na caixa de texto e insere no componente.
                showMessageText.setText(getString(R.string.hello_message, name));
                //Define o componente como visivel.
                showMessageText.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
