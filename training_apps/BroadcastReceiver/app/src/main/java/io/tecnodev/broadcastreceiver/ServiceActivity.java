package io.tecnodev.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import io.tecnodev.broadcastreceiver.shared.ExampleBindService;
import io.tecnodev.broadcastreceiver.shared.ExampleService;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnIniciarService, btnIniciarBindService, btnAgendamento;
    private TextView textViewService;
    private ExampleBindService exampleBindService;
    private boolean statusBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        textViewService = (TextView) findViewById(R.id.textViewService);

        btnIniciarBindService = (Button) findViewById(R.id.btnIniciarBindService);
        btnIniciarBindService.setOnClickListener(this);

        btnIniciarService = (Button) findViewById(R.id.btnIniciarService);
        btnIniciarService.setOnClickListener(this);

        btnAgendamento = (Button) findViewById(R.id.btnAgendamento);
        btnAgendamento.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIniciarService:
                Intent intentService = new Intent(this, ExampleService.class);

                if (isRunningService(intentService)) {
                    stopService(intentService);

                    textViewService.setText("SERVIÇO PARADO");
                } else {
                    startService(intentService);

                    textViewService.setText("SERVIÇO INICIADO");
                }
                break;
            case R.id.btnIniciarBindService:
                if(statusBind) {
                    String hora = exampleBindService.getHoras();
                    textViewService.setText("Data e hora do serviço: " + hora);
                }
                break;
            case R.id.btnAgendamento:
                Intent intentServiceAgendar = new Intent(this, ExampleService.class);
                PendingIntent pending = PendingIntent.getService(this, 1, intentServiceAgendar, 0);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.SECOND, 10);
                Long tempo = c.getTimeInMillis();

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, tempo, pending);

                Toast.makeText(this, "SERVIÇO AGENDADO", Toast.LENGTH_LONG).show();

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intentBindService = new Intent(this, ExampleBindService.class);
        bindService(intentBindService, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(statusBind) {
            unbindService(serviceConnection);
            statusBind = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ExampleBindService.LocalBinder localBinder = (ExampleBindService.LocalBinder) service;
            exampleBindService = localBinder.getService();

            statusBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            statusBind = false;
        }
    };

    private boolean isRunningService(Intent intent) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.getClassName().equals("io.tecnodev.broadcastreceiver.shared.ExampleService")) {
                return true;
            }
        }

        return false;
    }
}