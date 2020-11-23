package io.tecnodev.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentoCurso.OnListCourseSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onCourseSelected(long id) {
        FragmentoCursoDetalhe fragmentoCursoDetalhe = (FragmentoCursoDetalhe) getSupportFragmentManager().findFragmentById(R.id.fragmento_curso_detalhe);

        if(fragmentoCursoDetalhe != null) {
            fragmentoCursoDetalhe.load((int) id);
        } else {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("id", id);

            startActivity(intent);
        }
    }
}