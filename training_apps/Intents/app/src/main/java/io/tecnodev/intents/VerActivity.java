package io.tecnodev.intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerActivity extends AppCompatActivity {

    TextView txtNome, txtCurso;
    Button btnVoltar, btnAvaliar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        Bundle bundle = getIntent().getExtras();
        String textoNome = bundle.getString("nome");
        String textoCurso = bundle.getString("curso");

        txtNome = (TextView) findViewById(R.id.textViewNomeResposta);
        txtCurso = (TextView) findViewById(R.id.textViewCursoResposta);

        txtNome.setText(textoNome);
        txtCurso.setText(textoCurso);

        btnAvaliar = (Button) findViewById(R.id.btnAvaliar);
        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerActivity.this, AvaliacaoActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 0 && resultCode == RESULT_OK && data.hasExtra("nota")) {
            TextView txtNota = (TextView) findViewById(R.id.textViewNota);
            String nota = data.getExtras().getString("nota");

            txtNota.setText(nota);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}