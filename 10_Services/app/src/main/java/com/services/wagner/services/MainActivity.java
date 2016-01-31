package com.services.wagner.services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

/*
*   Services são aplicações que executam, em geral, processos longos em background
*   desprovidos de interface. Uma aplicação, pode requisitar a um serviço para fazer
*   um download ou mesmo executar uma música enquanto o usuário interage com a interface
*   oou mesmo sai da aplicação host. A aplicação e o Serviço podem ainda se comunicar.
*
*   Por padrão, um serviço sempre é executado na Thread principal da aplicação host.
*   Porém, isto pode ser configurado para que o serviço inicie outras threads quando é
*   chamado evitando assim que a interface trave durante uma execução que consuma
*   muito processamento. Para criar um serviço, é preciso declarar o nome da classe
*   no Manifest.
*
*       <service android:name=".ExampleService" />
*
*   O serviço pode ser utilizado por qualquer aplicação através de um Intent. Se
*   o serviço a ser implementado for apenas util para a aplicação que o contém, então
*   é preciso explicitar que o serviço é privado no Manifest.
*
*   <service ... android:export="false" />
* */

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            Object path = message.obj;
            if (message.arg1 == RESULT_OK && path != null){
                Toast.makeText(MainActivity.this,
                        getString(R.string.download_sucess,path.toString()),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this,
                        getString(R.string.download_error), Toast.LENGTH_LONG).show();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                Messenger messenger = new Messenger(handler);
                intent.putExtra("messenger", messenger);
                intent.setData(Uri.parse("cursos.html"));
                intent.putExtra("urlPath","http://k19.com.br/cursos");
                startService(intent);
            }
        });
    }//onCreate()
}//class MainActivity
