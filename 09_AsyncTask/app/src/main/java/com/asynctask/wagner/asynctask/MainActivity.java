package com.asynctask.wagner.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
*   Outra alternativa para utilizar concorrência no Android é utilizar AsyncTasks.
*   Um AsyncTask é um objeto que encapsula em uma interface simples o uso de threads.
*   Uma AsyncTask deve implementar obrigatoriamente o método doInBackground(), que é
*   exatamente a tarefa que está sendo executada em background. Caso seja necessário
*   alguma atualização na interface, é só sobrescrever o método onPostExecute(). Tudo
*   que estiver dentro deste método é executado na UI thread. Outro método interessante
*   que pode ser sobrescrito é o método onPreExecute() que é executado antes do
*   doInBackground() e que também é executado na UI thread.
* */
public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Button startButton;
    private ImageView imageView;
    private DownloadImageTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtêm a referência dos componentes da activity.
        imageView = (ImageView) findViewById(R.id.image_view);
        startButton = (Button) findViewById(R.id.start_button);

        //Define a ação a ser executada ao clicar no botão Iniciar.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Define o carregamento da barra de progresso que mostra a porcentagem de download da imagem.
                dialog = ProgressDialog.show(MainActivity.this,getString(R.string.download),getString(R.string.downloading));

                /*I
                    Instância o objeto responsável e inicia o download da imagem através
                    da url recebida pelo método execute().
                 */
                task = new DownloadImageTask();
                task.execute("https://avatars.githubusercontent.com/u/9699741?v=3");
            }
        });
    }//onCreate()

    //Sobrescreve o método a ser executado quando a activity for encerrada.
    @Override
    protected void onDestroy(){
        //Verifica se a barra de dialogo foi instânciada ou estava sendo exibida, afim de liberar os recursos alocados.
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        if (task != null){
            task.cancel(true);
        }
        super.onDestroy();
    }//onDestroy()

    /*
        Cria uma classe privada extendendo AysncTask que será a responsável por fazer o download da imagem
        em segundo plano, para não parar a execuçaõ da UI thread (thread main).
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        /*
            O método doInBackground deve ser sobrescrito obrigatóriamente, pois ele será o responsável
            por executar a tarefa em background, no caso deste exemplo, o download da imagem.
         */
        @Override
        protected Bitmap doInBackground(String... params){
            try {
                return downloadBitmap(params[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }//doInBackground()

        /*
            O método onPreExecute, deve ser sobreescrito, caso alguma tarefa deva ser executada antes
            da ação em background pela UI thread, no exemplo abaixo, ele apenas exibe a ProgressDialog
            que mostrará o andamento da execução da tarefa.
         */
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }//onPreExecute()

        /*
            Ao contrário do onPreExecute(), o método onPostExecute() é responsável por determinar a UI thread
            que execute uma tarefa logo após a tarefa em background ser executada. No exemplo, ele recebe a
            imagem obtida no download e atribui a area de exibição (ImageView).
         */
        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            dialog.dismiss();
            if (result != null){
                imageView.setImageBitmap(result);
            }
        }//onPostExecute()

        /*
            O método downloadBitmap() recebe uma String com o endereço da imagem, e retorna um objeto
            Bitmap com os dados da mesma, caso o endereço recebido seja válido. Caso não seja, retorna
            null.
         */
        private Bitmap downloadBitmap(String url) throws IOException{
            URL imageUrl = null;
            try {
                imageUrl = new URL(url);
            } catch (MalformedURLException e){
                e.printStackTrace();
                return null;
            }
            Bitmap bitmapImage = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();

                bitmapImage = BitmapFactory.decodeStream(is);
            } catch (IOException e){
                e.printStackTrace();
            }
            return bitmapImage;
        }//downloadBitmap()
    }//class DownloadImageTask
}//class MainActivity
