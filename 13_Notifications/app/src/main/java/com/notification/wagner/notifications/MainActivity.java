package com.notification.wagner.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtêm a referência do componente da activity e registra o tratador de eventos do mesmo.
        Button createNotification = (Button) findViewById(R.id.create_notification_button);
        createNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);

                /*
                    Cria um objeto PendingIntent para permitir o acesso da notificação a aplicação. Diferentemente
                    de uma intent, o PedingIntent é utilizado para fornecer acesso externo a sua aplicação, como no
                    exemplo, permitir que uma notificação seja exibida. Para definir o Objeto, é utilizado o método
                    getActivity() que recebe 4 parâmetros:

                        O contexto inicial de execução;
                        Código de envio privado para o remetente;
                        O Intent da activity que será executada;
                        Um Flag de controle para definir quais parte da intent podem ser fornecidas quando o envio acontecer.
                 */
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent,0);

                //Cria uma notificação e define o título, conteúdo, icone e o contexto da notificação.
                Notification notification = new Notification.Builder(MainActivity.this).setContentTitle(getString(R.string.new_notification))
                                                                                       .setContentText(getString(R.string.notification_content))
                                                                                       .setSmallIcon(R.drawable.ic_action_search)
                                                                                       .setContentIntent(pendingIntent)
                                                                                       .getNotification();

                //TODO Verificar depois se o operador abaixo possui a mesma finalidade que o mesmo em Ruby.
                notification.flags |= Notification.FLAG_AUTO_CANCEL;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,notification);
            }
        });

    }
}
