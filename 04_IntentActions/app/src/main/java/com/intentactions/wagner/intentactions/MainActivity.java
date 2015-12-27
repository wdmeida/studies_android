package com.intentactions.wagner.intentactions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
    É possível abrir outros aplicativos utilizando intents. Para isso, é necessário passar uma flag que
    chamamos de action. Dependendo do tipo de action que passarmos, umnovo aplicativo será aberto
    para executar a ação. Este tipo de intent é chamado de implícito, porque não é especificado qual a
    activity que será aberta. Apenas passamos uma ação, e o sistema irá decidir qual activity deverá ser
    utilizada nesse caso.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtém a referencia dos componentes da tela.
        Button viewSiteButton = (Button) findViewById(R.id.view_site_button);
        Button sendEmailButton = (Button) findViewById(R.id.send_email_button);
        Button makeCallButton = (Button) findViewById(R.id.make_call_button);

        //Define o listener a ser executado quando o botão visita url for apertado.
        viewSiteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k19.com.br"));
                startActivity(intent);
            }
        });

        //Define o listener a ser executado quando o botão email for apertado.
        sendEmailButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contato@k19.com.br"});
                startActivity(Intent.createChooser(intent,"Enviar email"));
            }
        });

        //Define o listener a ser executado quando o botão ligação for apertado.
        makeCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:2387-3791"));
                startActivity(intent);
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
