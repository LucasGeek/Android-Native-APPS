package io.tecnodev.widgets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JanelaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janela_data);

        Button btnData = (Button) findViewById(R.id.buttonJanela);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment janelaData = new JanelaDataFragmento();
                janelaData.show(getSupportFragmentManager(), "janelaData");
            }
        });
    }
}