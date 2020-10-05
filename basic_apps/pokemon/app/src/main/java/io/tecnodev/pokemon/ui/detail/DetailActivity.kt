package io.tecnodev.pokemon.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.constant.ConstantApp
import io.tecnodev.pokemon.shared.data.repository.PokemonApiDataSource
import io.tecnodev.pokemon.shared.data.repository.PokemonRepository
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    private var appBarLayout: AppBarLayout? = null

    private var collapsedMenu: Menu? = null
    private var appBarExpanded = true

    private var sharedString: String? = null

    private val viewModel: DetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository: PokemonRepository = PokemonApiDataSource()
                return DetailViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(detail_toolbar)

        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        appBarLayout = findViewById<AppBarLayout>(R.id.appbar)

        appBarLayout!!.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) > 200) {
                appBarExpanded = false
                invalidateOptionsMenu()
            } else {
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        })

        observeViewModelEvents()
        configureViewListeners()
        getPokemon()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        collapsedMenu = menu
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (collapsedMenu != null
            && (!appBarExpanded || collapsedMenu!!.size() != 1)
        ) {
            //collapsed
            collapsedMenu!!.add("shared")
                .setIcon(R.drawable.ic_share)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == "shared") {
            sharedData()
        }

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        observeViewModelEvents()
        configureViewListeners()
        getPokemon()
    }

    private fun observeViewModelEvents() {
        viewModel.pokemonLiveDataModel.observe(this, Observer {
            Glide.with(applicationContext)
                .load(it.img)
                .into(header)

            val toolbarLayout =
                findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)

            toolbarLayout.title = it.name

            heightPokemon.text = it.height.toString()
            weightPokemon.text = it.weight.toString()
            experiencePokemon.text = it.base_experience.toString()

            sharedString = "Nome: ${it.name} || Altura: ${it.height} || Largura: ${it.weight} || Base de experiência: ${it.base_experience}"
        })
    }

    private fun getPokemon() {
        var urlType = getIntent().getExtras()!!.getString("url")
        var urlReplaced = urlType?.replace(ConstantApp.SERVICE.BASE_URL, "")
        var idPokemon = urlReplaced?.filter { it.isDigit() }

        viewModel.getPokemon(idPokemon?.toInt()!!)
    }

    private fun configureViewListeners() {
        fabSharedInfo.setOnClickListener {
            sharedData()
        }
    }

    private fun sharedData() {
        if(!sharedString.isNullOrEmpty()) {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND

                putExtra(Intent.EXTRA_TEXT, sharedString)

                type="text/plain"

                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }, "Compartilhar")

            startActivity(share)
        }
    }
}