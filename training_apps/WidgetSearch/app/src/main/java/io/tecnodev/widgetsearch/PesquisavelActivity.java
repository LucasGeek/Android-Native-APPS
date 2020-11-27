package io.tecnodev.widgetsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PesquisavelActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisavel);

        String[] estados = {"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Paraná","Paraíba","Pará","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondonia","Roraima","Santa Catarina","Sergipe","São Paulo","Tocantins"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estados);
        ListView list = (ListView) findViewById(R.id.listResultado);
        list.setAdapter(adapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SugestaoPesquisaProvider.AUTORITY, SugestaoPesquisaProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            pesquisar(query);
        } else {
            finish();
        }
    }

    private void pesquisar(String query) {
        adapter.getFilter().filter(query);
        adapter.notifyDataSetChanged();
    }
}