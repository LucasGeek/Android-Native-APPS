package io.tecnodev.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.tecnodev.broadcastreceiver.shared.ExampleJobService;

public class JobActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        Button btnAgendar = (Button) findViewById(R.id.btnAgendarJob);
        btnAgendar.setOnClickListener(this);

        Button btnCancelar = (Button) findViewById(R.id.btnCancelarJob);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgendarJob:
                ComponentName jobComponente = new ComponentName(this, ExampleJobService.class);

                JobInfo.Builder builder = new JobInfo.Builder(1, jobComponente);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                JobInfo jobInfo = builder.build();
                jobScheduler.schedule(jobInfo);

                Toast.makeText(this, "Job Agendado!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancelarJob:
                JobScheduler jobSchedulerCancel = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                jobSchedulerCancel.cancelAll();

                Toast.makeText(this, "Job Cancelado!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}