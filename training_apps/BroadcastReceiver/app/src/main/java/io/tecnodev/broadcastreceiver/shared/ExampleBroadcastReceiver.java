package io.tecnodev.broadcastreceiver.shared;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import io.tecnodev.broadcastreceiver.MainActivity;

public class ExampleBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BROADCAST", intent.getAction());

        try {
            Intent i = new Intent(context, WorkerService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(i);
            } else {
                context.startService(i);
            }

        } catch(Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(context, MainActivity.class);

            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Connectivity changed", Toast.LENGTH_SHORT).show();
        }
    }
}
