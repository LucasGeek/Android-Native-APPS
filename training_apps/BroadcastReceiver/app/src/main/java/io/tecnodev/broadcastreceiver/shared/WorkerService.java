package io.tecnodev.broadcastreceiver.shared;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import io.tecnodev.broadcastreceiver.MainActivity;
import io.tecnodev.broadcastreceiver.R;

public class WorkerService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String NEW_CHANNEL_ID = "AndroidForegroundServiceChannel";

    Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //para Android Oreo acima do canal de notificação obrigatório
            createNotificationChannel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // se o Android 10 criar uma intenção pendente e uma notificação de tela inteira

                Intent fullScreenIntent = new Intent(this, MainActivity.class);

                // Para a abertura da atividade quando a notificação clicada
                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 2022, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new NotificationCompat.Builder(this, NEW_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Notification title")
                        .setContentText("Notification Text")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_REMINDER)
                        .setFullScreenIntent(fullScreenPendingIntent, true)
                        .build();

                startForeground(2, notification);
            } else {
                // se abaixo do Android 10 criou uma notificação para serviço de primeiro plano porque é obrigatório
                Intent notificationIntent = new Intent(this, MainActivity.class);

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0022,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentText("Foreground Service")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setSound(null)
                        .setContentIntent(pendingNotificationIntent)
                        .build();

                // para atividades iniciadas abaixo do Android 10
                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                getApplicationContext().startActivity(i);
            }


            startForeground(1, notification);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Foreground Service fault", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NEW_CHANNEL_ID,
                    "Android Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            serviceChannel.setSound(null, null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            serviceChannel.setSound(null, null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}