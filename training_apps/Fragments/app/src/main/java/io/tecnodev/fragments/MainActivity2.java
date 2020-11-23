package io.tecnodev.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(getIntent().hasExtra("id")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentoCursoDetalhe fragmentoCursoDetalhe = new FragmentoCursoDetalhe();

            fragmentTransaction.add(R.id.fragmento_box, fragmentoCursoDetalhe, "fragmentoCursoDetalhe");
            fragmentTransaction.commit();
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        int id = (int) getIntent().getExtras().getLong("id");
        FragmentoCursoDetalhe fragmentoCursoDetalhe = (FragmentoCursoDetalhe) getSupportFragmentManager().findFragmentByTag("fragmentoCursoDetalhe");

        if(fragmentoCursoDetalhe != null) {
            fragmentoCursoDetalhe.load(id);
        }
    }
}