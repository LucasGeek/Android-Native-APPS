package io.tecnodev.broadcastreceiver.shared;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class ExampleService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE", "Serviço iniciado!!");
        horas();
        return super.onStartCommand(intent, flags, startId);
    }

    public void horas() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                Date data = new Date();
                Log.i("SERVICE", "Hora:" + data.toString());
            } catch (InterruptedException e) {
                Log.e("SERVICE", "Error:" + e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICE", "Serviço encerrado!!");
    }
}
