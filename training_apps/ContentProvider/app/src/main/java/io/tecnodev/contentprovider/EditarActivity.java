package io.tecnodev.contentprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import io.tecnodev.contentprovider.shared.LivroDbHelper;
import io.tecnodev.contentprovider.shared.LivrosProvider;

public class EditarActivity extends AppCompatActivity {
    private static final int LOADER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra("id")) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_ID, intent.getExtras(), loaderCallbacks);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_editar_livro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarLivro:
                alterar();
                return true;
            case R.id.excluirLivro:
                delete();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void delete() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setIcon(android.R.drawable.ic_menu_delete)
                .setTitle("Confirmação")
                .setMessage("Tem certeza que deseja excluir esse registro?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtId = (EditText) findViewById(R.id.edtId);
                        String id = edtId.getText().toString();

                        getContentResolver().delete(LivrosProvider.CONTENT_URI, LivroDbHelper.C_ID + "=?", new String[]{id});
                        Toast.makeText(getApplicationContext(), "Livro excluido", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }
                });

        builder.show();
    }

    public void alterar() {
        EditText edtId = (EditText) findViewById(R.id.edtId);
        EditText edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        EditText edtAutor = (EditText) findViewById(R.id.edtAutor);

        String id = edtId.getText().toString();
        String autor = edtAutor.getText().toString();
        String titulo = edtTitulo.getText().toString();

        ContentValues values = new ContentValues();

        values.put(LivroDbHelper.C_TITULO, titulo);
        values.put(LivroDbHelper.C_AUTOR, autor);

        getContentResolver().update(LivrosProvider.CONTENT_URI, values, LivroDbHelper.C_ID + "=?", new String[]{id});

        startActivity(new Intent(this, MainActivity.class));
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            if(id == LOADER_ID) {
                String idLivro = "";
                idLivro = args.getString("id");

                Uri uri = Uri.withAppendedPath(LivrosProvider.CONTENT_URI, "id/" + idLivro);

                return new CursorLoader(getApplicationContext(), uri, null, null, null, null);
            } else {
                return null;
            }
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if(loader.getId() == LOADER_ID) {
                String id = "";
                String autor = "";
                String titulo = "";

                if(data.moveToNext()) {
                    id = data.getString(0);
                    titulo = data.getString(1);
                    autor = data.getString(2);
                }

                EditText edtId = (EditText) findViewById(R.id.edtId);
                EditText edtTitulo = (EditText) findViewById(R.id.edtTitulo);
                EditText edtAutor = (EditText) findViewById(R.id.edtAutor);

                edtId.setText(id);
                edtTitulo.setText(titulo);
                edtAutor.setText(autor);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };
}