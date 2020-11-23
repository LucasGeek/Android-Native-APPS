package io.tecnodev.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome, edtCurso;
    Button btnProximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = (EditText) findViewById(R.id.editNome);
        edtCurso = (EditText) findViewById(R.id.editCurso);

        btnProximo = (Button) findViewById(R.id.btnProximo);
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeNome = edtNome.getText().toString();
                String nomeCurso = edtCurso.getText().toString();

                Intent intent = new Intent(CadastroActivity.this, VerActivity.class);
                intent.putExtra("nome", nomeNome);
                intent.putExtra("curso", nomeCurso);

                startActivity(intent);
            }
        });
    }
}