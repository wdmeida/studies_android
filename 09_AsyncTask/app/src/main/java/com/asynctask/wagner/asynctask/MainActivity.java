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

        imageView = (ImageView) findViewById(R.id.image_view);
        startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(MainActivity.this,getString(R.string.download),getString(R.string.downloading));
                task = new DownloadImageTask();
                task.execute("http://k19.com.br/css/img/main-header-logo.png");
            }
        });
    }//onCreate()

    @Override
    protected void onDestroy(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        if (task != null){
            task.cancel(true);
        }
        super.onDestroy();
    }//onDestroy()

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params){
            try {
                return downloadBitmap(params[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }//doInBackground()

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }//onPreExecute()

        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            dialog.dismiss();
            if (result != null){
                imageView.setImageBitmap(result);
            }
        }//onPostExecute(0

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
