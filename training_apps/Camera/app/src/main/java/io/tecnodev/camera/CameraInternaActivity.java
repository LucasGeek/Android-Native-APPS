package io.tecnodev.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class CameraInternaActivity extends AppCompatActivity {
    private Camera camera;
    private CameraPreview cameraPreview;
    private Button btnCapturar;

    private MediaRecorder mediaRecorder;
    private boolean gravando = false;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_interna);

        verifyStoragePermissions(this);

        camera = getCameraInstance();

        cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.framePreview);
        preview.addView(cameraPreview);

        btnCapturar = (Button) findViewById(R.id.btnCamera);
        btnCapturar.setOnClickListener(clickListenerVideo);
//        btnCapturar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                camera.takePicture(null, null, pictureCallback);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
        releaseMediaRecorder();
    }

    private View.OnClickListener clickListenerVideo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(gravando) {
                mediaRecorder.stop();
                releaseMediaRecorder();
                camera.lock();

                btnCapturar.setText("Gravar");
                gravando = false;
            } else {
                if (prepareVideoRecorder()) {
                    mediaRecorder.start();
                    btnCapturar.setText("Parar");
                    gravando = true;
                } else {
                    releaseMediaRecorder();
                }
            }
        }
    };

    private boolean prepareVideoRecorder() {
        mediaRecorder = new MediaRecorder();

        camera.unlock();
        mediaRecorder.setCamera(camera);

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        mediaRecorder.setOutputFile(getOutputMediaFile(2).toString());
        mediaRecorder.setPreviewDisplay(cameraPreview.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
            return true;
        } catch (Exception e) {
            releaseMediaRecorder();
            return false;
        }
    }

    private static Camera getCameraInstance () {
        Camera c = null;

        try {
            c = Camera.open();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return c;
    }

    private void releaseCamera() {
        if(camera != null) {
            camera.release();
            camera = null;
        }
    }

    private void releaseMediaRecorder() {
        if(mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            camera.lock();
        }
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(1);

            if(pictureFile == null) {
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }
    };

    private File getOutputMediaFile(int type) {
        File mediaFile = null;
        File mediaStorangeDir = new File(Environment.getExternalStorageDirectory() + "/" + "foto_e_video");
        if(!mediaStorangeDir.exists()) {
            try {
                if(!mediaStorangeDir.createNewFile()) {
                    Log.d("Erro File:", "Erro ao criar o diretório");
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String nameFile = UUID.randomUUID().toString();

        if(type == 1) {
            mediaFile = new File(mediaStorangeDir.getPath() + File.separator + "IMG_" + nameFile + ".jpg");
        } else if(type == 2) {
            mediaFile = new File(mediaStorangeDir.getPath() + File.separator + "VID_" + nameFile + ".mp4");
        }

        return mediaFile;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
