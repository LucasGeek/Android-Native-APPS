package io.tecnodev.widgets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewSpinnerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ArrayAdapter<String> adapterHabilidades;
    ArrayAdapter<String> adapterArea;
    ArrayList<String> habilidades;
    Spinner spnArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_spinner);

        habilidades = new ArrayList<String>();
        adapterHabilidades = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habilidades);

        String[] areaAtuacao = {"Desenvolvimento", "Segurança da informação", "Infraestrutura"};
        adapterArea = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, areaAtuacao);

        spnArea = (Spinner) findViewById(R.id.spinnerArea);
        spnArea.setAdapter(adapterArea);

        ListView listHabilidades = (ListView) findViewById(R.id.listViewHabilidades);
        listHabilidades.setAdapter(adapterHabilidades);
        listHabilidades.setOnItemClickListener(this);

        Button btnSelecionar = (Button) findViewById(R.id.btnSelecionar);
        btnSelecionar.setOnClickListener(this);

        Button btnADD = (Button) findViewById(R.id.btnAdicionar);
        btnADD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelecionar:
                TextView txtResultado = (TextView) findViewById(R.id.txtAreaSelecionada);
                String txtSpinner = spnArea.getSelectedItem().toString();

                txtResultado.setText("A área selecionada é: " + txtSpinner + " com as habilidades a abaixo");
                break;
            case R.id.btnAdicionar:
                EditText edtHabilidades = (EditText) findViewById(R.id.editHabilidade);
                String txtHabilidades = edtHabilidades.getText().toString();

                if(!txtHabilidades.isEmpty()) {
                    habilidades.add(txtHabilidades);
                    adapterHabilidades.notifyDataSetChanged();

                    edtHabilidades.setText("");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);

        msgBox.setTitle("Excluindo...");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Deseja realmente excluir este item?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                habilidades.remove(position);
                adapterHabilidades.notifyDataSetChanged();
            }
        });
        msgBox.setNegativeButton("Não", null);
        msgBox.show();
    }
}