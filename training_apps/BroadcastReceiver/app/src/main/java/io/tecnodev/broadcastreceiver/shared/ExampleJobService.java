package io.tecnodev.broadcastreceiver.shared;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class ExampleJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "Job iniciado", Toast.LENGTH_LONG);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "Job parado!", Toast.LENGTH_LONG);
        return true;
    }
}
