package io.tecnodev.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentoCursoDetalhe extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_curso_detalhe, container, false);

        return view;
    }

    public void load(int id) {
        TextView txtCurso = (TextView) getView().findViewById(R.id.textViewCurso);

        List<String> cursos = new ArrayList<>();

        cursos.add("Android Basico");
        cursos.add("Android Intermediario");
        cursos.add("Android Avançado");

        String curso = cursos.get(id);

        txtCurso.setText(curso);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        TextView txtCurso = (TextView) getView().findViewById(R.id.textViewCurso);
        String textCurso = txtCurso.getText().toString();

        outState.putString("curso", textCurso);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            TextView txtCurso = (TextView) getView().findViewById(R.id.textViewCurso);
            String nomeCurso = savedInstanceState.getString("curso");

            txtCurso.setText(nomeCurso);
        }
    }
}
