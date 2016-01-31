package com.services.wagner.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/*
 *  Para criar um serviço é preciso implementar uma extensão da classe Service e
 *  sobrescrever alguns métodos de callback.
 *
 *  onStartCommand() - Método que inicia um serviço indefinidamente. O serviço apenas
 *  termina quando o método stopSelf() é executado a partir do próprio serviço ou quando
 *  o método stopService() é executado a partir de outra aplicação. O método devolve um inteiro.
 *  Este valor indica como o sistema deve continuar o serviço caso o sistema o mate.
 *  Existem 3 valores possíveis:
 *
 *      START_NOT_STICKY - Não reinicia o serviço a menos que haja Intents a serem entregues.
 *
 *      START_STICKY - Reinicia o serviço mas não continua a partir do Intent que estava em
 *      execução mas apenas para os que estavam pendentes;
 *
 *      START_REDELIVER_INTENT - Reinicia o serviço retornando a partir do Intent que estava
 *      em execução.
 *
 *  onBind() - Método que é chamado pelo sistema para associar o serviço a uma aplicação.
 *  Ele deve prover uma interface de comunicação entre ambos. Este método deve ser implementado
 *  obrigatóriamente, logo, se o serviço não for desenhado para suportar Bind então o método
 *  onBind() deve devolver null.
 *
 *  onCreate() - Método chamado pelo sistema no momento da criação do serviço e pode ser
 *  utilizado para realizar pré configurações.
 *
 *  onDestroy() - Método chamado pelo sistema quando o serviço for destruído e pode ser utilizado
 *  para liberar recursos utilizados.
 *
 *  É muito comum implementar serviços que utilizem sua própria thread para executar as tarefas
 *  requisitadas, desta forma, o framework fornece uma extensão da classe Service que simplifica a
 *  criação de serviçoes como mostrado na classe abaixo. O código abaixo implementa um serviço através
 *  da classe IntentService.
 */
public class DownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;

    /*
        O construtor é obrigatório e deve chamar o contrutor da super classe
        passando o nome da Thread worker.
     */
    public DownloadService() {
        super("DownloadService");
    }

    /*
        Este método é chamado pela IntentService a partir de um worker Thread e recebe o
        Intent que iniciou o serviço. Quando o método termina o IntentService para o serviço.
        Quando se utiliza a classe IntentService, não é necessário se preocupar em parar o serviço.
     */
    @Override
    protected void onHandleIntent(Intent intent){
        Uri data = intent.getData();
        String urlPath = intent.getStringExtra("urlPath");
        String fileName = data.getPath();
        File output = new File(Environment.getExternalStorageDirectory(), fileName);
        if (output.exists()){
            output.delete();
        }

        InputStream stream = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(urlPath);
            stream = url.openConnection().getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            fos = new FileOutputStream(output.getPath());
            int next;
            while ((next = reader.read()) != -1){
                fos.write(next);
            }
            result = Activity.RESULT_OK;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (stream != null){
                try {
                    stream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        Bundle extras = intent.getExtras();
        if (extras != null){
            Messenger messenger = (Messenger) extras.get("messenger");
            Message msg = Message.obtain();
            msg.arg1 = result;
            msg.obj = output.getAbsolutePath();

            try {
                messenger.send(msg);
            } catch (android.os.RemoteException e1){
                Log.e("DownloadService", "Erro ao enviar mensagem", e1);
            }
        }
    }//onHandleIntent()
}//class DownloadService
