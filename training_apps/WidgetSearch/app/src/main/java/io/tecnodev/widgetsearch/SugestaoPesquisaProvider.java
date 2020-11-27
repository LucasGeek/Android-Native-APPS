package io.tecnodev.widgetsearch;

import android.content.SearchRecentSuggestionsProvider;

class SugestaoPesquisaProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTORITY = "io.tecnodev.pesquisa";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SugestaoPesquisaProvider() {
        setupSuggestions(AUTORITY, MODE);
    }
}