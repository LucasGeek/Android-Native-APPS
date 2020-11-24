package io.tecnodev.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import io.tecnodev.contentprovider.shared.LivroDbHelper;
import io.tecnodev.contentprovider.shared.LivrosProvider;

public class InserirActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_inserir_livro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarLivro:
                save();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {
        EditText edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        EditText edtAutor = (EditText) findViewById(R.id.edtAutor);

        String titulo = edtTitulo.getText().toString();
        String autor = edtAutor.getText().toString();

        ContentValues values = new ContentValues();

        values.put(LivroDbHelper.C_TITULO, titulo);
        values.put(LivroDbHelper.C_AUTOR, autor);

        ContentResolver provedor = getContentResolver();

        provedor.insert(LivrosProvider.CONTENT_URI, values);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}