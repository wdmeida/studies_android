package com.notification.wagner.notifications;

import android.app.Activity;
import android.os.Bundle;

/*
 *  Outro estilo de notificação é o conhecido como Status Bar Notification, que são
 *  aqueles alertas que aparecem na barra de status. Existem diferentes tipos de alertas
 *  que podem ser criados. Em todos os caos, você deve utilizar a classe NotificationManager
 *  para enviar as notificações para o sistema. Para construir uma notificação, é utilizado
 *  o Notification.Builder() que possui diferentes métodos que customizam o conteúdo e aparência
 *  da notificação.
 */
public class NotificationActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }
}//class NotificationActivity
