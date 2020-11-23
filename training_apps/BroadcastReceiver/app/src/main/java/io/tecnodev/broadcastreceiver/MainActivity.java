package io.tecnodev.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import io.tecnodev.broadcastreceiver.shared.ExampleBroadcastReceiver;
import io.tecnodev.broadcastreceiver.shared.ExampleService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ExampleBroadcastReceiver exemplorReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEstatico = (Button) findViewById(R.id.btnEstatico);
        btnEstatico.setOnClickListener(this);

        Button btnDinamico = (Button) findViewById(R.id.btnDinamico);
        btnDinamico.setOnClickListener(this);

        Button btnAgendarB = (Button) findViewById(R.id.btnAgendarB);
        btnAgendarB.setOnClickListener(this);

        Button btnAgendarRepetir = (Button) findViewById(R.id.btnAgendarRepetir);
        btnAgendarRepetir.setOnClickListener(this);

        Button btnAgendarCancelar = (Button) findViewById(R.id.btnAgendarCancelar);
        btnAgendarCancelar.setOnClickListener(this);

        exemplorReceiver = new ExampleBroadcastReceiver();

        registerReceiver(exemplorReceiver, new IntentFilter("BRADCAST_DINAMICO"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEstatico:
                sendBroadcast(new Intent("BRADCAST_ESTATICO"));
                break;
            case R.id.btnDinamico:
                sendBroadcast(new Intent("BRADCAST_DINAMICO"));
                break;
            case R.id.btnAgendarB:
                Intent intentBroadcastAgendar = new Intent("BRADCAST_ESTATICO");
                PendingIntent pending = PendingIntent.getBroadcast(this, 2, intentBroadcastAgendar, 0);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.SECOND, 10);
                Long tempo = c.getTimeInMillis();

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, tempo, pending);

                Toast.makeText(this, "Bradcast agendado", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnAgendarRepetir:
                Intent intentBroadcastAgendarRepetir = new Intent("BRADCAST_ESTATICO");
                PendingIntent pendingRepetir = PendingIntent.getBroadcast(this, 2, intentBroadcastAgendarRepetir, 0);

                Calendar calendarRepetir = Calendar.getInstance();
                calendarRepetir.add(Calendar.SECOND, 10);
                Long tempoRepetir = calendarRepetir.getTimeInMillis();

                AlarmManager alarmManagerRepetir = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerRepetir.setRepeating(AlarmManager.RTC, tempoRepetir, 60000, pendingRepetir);

                Toast.makeText(this, "Bradcast agendado - Repetir", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnAgendarCancelar:
                Intent intentBroadcastAgendarCancelar = new Intent("BRADCAST_ESTATICO");
                PendingIntent pendingCancelar = PendingIntent.getBroadcast(this, 2, intentBroadcastAgendarCancelar, 0);

                AlarmManager alarmManagerCancelar = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerCancelar.cancel(pendingCancelar);

                Toast.makeText(this, "Bradcast agendado - Cancelado", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        Toast.makeText(this, "Intent Enviada!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(exemplorReceiver);
    }
}