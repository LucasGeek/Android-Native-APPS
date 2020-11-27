package io.tecnodev.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCameraExternal).setOnClickListener(this);
        findViewById(R.id.btnCameraInternal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCameraExternal:
                startActivity(new Intent(MainActivity.this, CameraExternaActivity.class));
                break;
            case R.id.btnCameraInternal:
                startActivity(new Intent(MainActivity.this, CameraInternaActivity.class));
                break;
        }
    }
}