package io.tecnodev.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentoCurso extends Fragment {

    OnListCourseSelectedListener callback;

    public interface OnListCourseSelectedListener {
        public void onCourseSelected(long id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_curso, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        String[] cursos = {"Android Basico", "Android Intermediario", "Android Avançado"};

        ArrayAdapter<String> adapterCursos = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cursos);

        ListView list = (ListView) getView().findViewById(R.id.listViewCursos);
        list.setAdapter(adapterCursos);
        list.setOnItemClickListener(clickItemListener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        callback = (OnListCourseSelectedListener) context;
    }

    private AdapterView.OnItemClickListener clickItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            callback.onCourseSelected(id);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdicionar:
                Toast.makeText(getActivity(), "Menu selecionado: Adicionar", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuEditar:
                Toast.makeText(getActivity(), "Menu selecionado: Editar", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
