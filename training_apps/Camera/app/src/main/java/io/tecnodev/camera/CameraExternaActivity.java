package io.tecnodev.camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class CameraExternaActivity extends AppCompatActivity {
    //ImageView imgFoto;
    VideoView video;

    private Uri uri_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_externa);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        //imgFoto = (ImageView) findViewById(R.id.imgCamera);
        video = (VideoView) findViewById(R.id.videoView);
        findViewById(R.id.btnIniciarCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravarVideo();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == 1 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imagem = (Bitmap) extras.get("data");
//            imgFoto.setImageBitmap(imagem);
//        }

        if(requestCode == 2 && resultCode == RESULT_OK) {
            try {
                MediaController mc = new MediaController(this);
                video.setMediaController(mc);
                video.setVideoURI(uri_video);
                video.requestFocus();
                video.start();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void tirarFoto() {
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentFoto, 1);
    }

    public void gravarVideo() {
        String arquivo = Environment.getExternalStorageDirectory() + "/" +  System.currentTimeMillis() + ".mp4";
        File file = new File(arquivo);

        if (Build.VERSION.SDK_INT < 24) {
            uri_video = Uri.fromFile(file);
        } else {
            uri_video = Uri.parse(file.getPath());
        }

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_video);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, 2);
    }
}