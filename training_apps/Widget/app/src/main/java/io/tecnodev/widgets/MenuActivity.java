package io.tecnodev.widgets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MenuActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        toolbar.inflateMenu(R.menu.activity_menu);
        toolbar.setTitle("Toolbar");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_camera);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView txtResultado = (TextView) findViewById(R.id.txtMenuSelecionar);
        String txt = "Menu selecionado: ";

        switch (item.getItemId()) {
            case R.id.menuAdicionar:
                txt += "Adicionar";
                break;

            case R.id.menuAjuda:
                txt += "Ajuda";
                break;

            case R.id.menuDeletar:
                txt += "Deletar";
                break;

            case R.id.menuEditar:
                txt += "Editar";
                break;

            case R.id.menuSalvar:
                txt += "Salvar";
                break;

            case R.id.menuUpload:
                txt += "Upload";
                break;

            case R.id.menuPreferencias:
                txt += "Preferencias";
                break;

            case android.R.id.home:
                txt += "Drawer";
                break;
        }

        Toast toast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
        toast.setGravity(0,0, 50);
        toast.show();

        txtResultado.setText(txt);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView txtResultado = (TextView) findViewById(R.id.txtMenuSelecionar);
        String txt = "Menu selecionado: ";

        switch (item.getItemId()) {
            case R.id.menuAdicionar:
                txt += "Adicionar";
                break;

            case R.id.menuAjuda:
                txt += "Ajuda";
                break;

            case R.id.menuDeletar:
                txt += "Deletar";
                break;

            case R.id.menuEditar:
                txt += "Editar";
                break;

            case R.id.menuSalvar:
                txt += "Salvar";
                break;

            case R.id.menuUpload:
                txt += "Upload";
                break;

            case R.id.menuPreferencias:
                txt += "Preferencias";
                break;

            case android.R.id.home:
                txt += "Drawer";
                break;
        }

        txtResultado.setText(txt);

        return false;
    }
}