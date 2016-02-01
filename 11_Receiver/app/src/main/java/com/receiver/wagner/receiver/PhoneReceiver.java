package com.receiver.wagner.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/*
 *  Um BroadCast Receiver é um objeto que herda BroadCastReceiver, e que implementa o método
  * onReceive(). Eles devem ser registrados no AndroidManifest.xml. Um receiver em geral deve
  * ser usado para receber notificações do sistema, e executar uma tarefa dependendo do tipo de
  * notificação recebido. Por exemplo, se sua aplicação recebeu uma notificação de que a bateria está
  * baixa, ele pode forçadamente parar servidos ou tarefas que estejam consumindo muito processamento, ou
  * até fechar o aplicativo.
 */
public class PhoneReceiver extends BroadcastReceiver{
    private static final String TAG = "PhoneReceiver";

    @Override
    public void onReceive(Context context, Intent intent){
        Bundle extras = intent.getExtras();
        if (extras != null){
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.w(TAG, state);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.w(TAG, phoneNumber);
            }
        }
    }//onReceive()
}//class PhoneReceiver
