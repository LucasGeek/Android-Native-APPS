package io.tecnodev.contentprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import io.tecnodev.contentprovider.shared.LivroDbHelper;
import io.tecnodev.contentprovider.shared.LivrosProvider;

public class MainActivity extends AppCompatActivity {
    private static final int LOADER_ID = 1;
    private SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String[] from = {LivroDbHelper.C_ID, LivroDbHelper.C_TITULO, LivroDbHelper.C_AUTOR};
        int[] to = {R.id.txtId, R.id.txtTitulo, R.id.txtAutor};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_main_items, null, from, to, 0);

        ListView list = (ListView) findViewById(R.id.listLivros);
        list.setAdapter(simpleCursorAdapter);
        list.setOnItemClickListener(itemClickListener);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, cursorLoaderCallbacks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addLivro:
                startActivity(new Intent(this, InserirActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            return new CursorLoader(getApplicationContext(), LivrosProvider.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if(loader.getId() == LOADER_ID) {
                simpleCursorAdapter.swapCursor(data);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            simpleCursorAdapter.swapCursor(null);
        }
    };

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getBaseContext(), EditarActivity.class);
            intent.putExtra("id", String.valueOf(id));
            startActivity(intent);
        }
    };
}