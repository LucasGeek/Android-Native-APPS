package io.tecnodev.armazenamentodados;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LerContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_content_provider);

        try {
            Uri uriLivros = Uri.parse("content://io.tecnodev.contentprovider");
            Cursor cursor = getContentResolver().query(uriLivros, null, null, null, null);

            String[] from = {"titulo", "autor"};
            int[] to = {R.id.txtTitulo, R.id.txtAutor};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_ler_content_provider_items, cursor, from, to);

            ListView list = (ListView) findViewById(R.id.listLivros);
            list.setAdapter(simpleCursorAdapter);

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }
}