package io.tecnodev.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnExibir, btnExibirProgresso;
    private ImageView imageLogo;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExibirProgresso = (Button) findViewById(R.id.btn_exibir_loading);
        btnExibirProgresso.setOnClickListener(this);

        btnExibir = (Button) findViewById(R.id.btn_exibir_imagem);
        btnExibir.setOnClickListener(this);

        imageLogo = (ImageView) findViewById(R.id.image_logo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exibir_imagem:
                String url = "https://www.google.com.br/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";

//                Runnable processo = new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            final Bitmap imagem = exibirImagemDaWeb(url);
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    imageLogo.setImageBitmap(imagem);
//                                }
//                            });
//                        } catch (Exception e) {
//                            Log.e("Error", e.getMessage());
//                        }
//                    }
//                };
//
//                Thread tarefa = new Thread(processo);
//                tarefa.start();

                new DownloadImagem().execute(url);

                break;
            case R.id.btn_exibir_loading:
                exibirProgressDialog();
                break;
        }
    }

    private Bitmap exibirImagemDaWeb(String url) {
        Bitmap bitmap = null;

        try {
            URL src = new URL(url);
            InputStream inputStream = (InputStream) src.getContent();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  bitmap;
    }

    private class DownloadImagem extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            return exibirImagemDaWeb(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageLogo.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }

    private void exibirProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Aguarde...");
        progressDialog.show();

        new Thread() {
            int total = 0;
            @Override
            public void run() {
                super.run();

                while (total < 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setProgress(total);
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    total++;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(), "Ação concluída!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }.start();
    }
}