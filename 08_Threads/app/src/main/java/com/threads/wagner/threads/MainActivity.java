package com.threads.wagner.threads;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
/*
*   No Android, existe uma thread principal que é responsavel por desenhar a tela e lidar com os
*   eventos de toque na tela. Esta thread é conhecida como UI thread (USER INTERFACE THREAD), ou
*   também como main thread. Se o desenvolvedor não utilizar nenhum tipo de concorrência, todo o
*   código que escrever irá rodar nesta thread principal. Isso se torna um problema para tarefas
*   que levam muito tempo a serem executadas, pois enquanto a tarefa está sendo executada, a interface
*   para de responder a eventos, como toques feitos pelo usuário.
*
*   Se houver qualquer processamento que ocupe a UI thread por mais de 5 segundos, a aplicação irá
*   receber automaticamente um ANR (Application not responding), e o sistema irá fechar a aplicação.
*   Por isso, qualquer processamento mais lento deve ser feito em outras threads para não ocupar a
*   UI thread.
*
*   No Android é suportado o mesmo tipo de concorrência dos demais aplicativos Java. Podemos utilizar
*   threads, que executam objetos do tipo Runnable. O único porém, é que não podemos alterar nada relatico
*   a UI dentro destas trheads que rodam em background. Apenas a UI thread é que pode alterar a UI. Para
*   contornar esse problema podemos utilizar Handlers. Um Handler é um objeto que possui o método post(Runnable).
*   O Runnable que é passado ao método post é executado posteriormente dentro de main thread e por isso pode
*   realizar alterações na interface da aplicação.
*
*   Outra alternativa que não envolve criar um Handler é utilizar o método runOnUiThread(Runnable), que
*   pertence a Activity. O Runnable que é passado a este método também é executado dentro da main thread.
* */
public class MainActivity extends AppCompatActivity {
    private ProgressBar progress;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtém a referência dos componentes do layout (tela).
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        startButton = (Button) findViewById(R.id.start_button);

        //Seta a ação a ser executada quando o usuário clicar no botão iniciar.
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Cria um objeto Runnable com a ação a ser executada dentro da UI thread.
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 10; i++){
                            final int value = i;
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }

                            /*Define a ação que será executada pela UI thread através
                            * do método runOnUiThread (lembrando que apenas a thread main
                            * pode alterar a interface, por isso é necessário a utilização deste
                            * método ou a criação de um objeto do tipo Handler, no caso, ele vai atualizar
                            * a barra de progresso a cada incremento do valor.).*/
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setProgress(value);
                                }
                            });
                        }
                    }
                };

                //Cria uma thread, atribui o objeto runnable e manda iniciar.
                new Thread(runnable).start();
            }
        });
    }
}//class MainActivity
