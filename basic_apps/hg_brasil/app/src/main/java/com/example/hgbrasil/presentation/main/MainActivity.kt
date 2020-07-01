package com.example.hgbrasil.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.hgbrasil.R
import com.example.hgbrasil.presentation.main.fragments.about.AboutFragment
import com.example.hgbrasil.presentation.main.fragments.api.ApiFragment
import com.example.hgbrasil.presentation.main.fragments.finance.FinanceFragment
import com.example.hgbrasil.presentation.main.fragments.geo.GeoFragment
import com.example.hgbrasil.presentation.main.fragments.home.HomeFragment
import com.example.hgbrasil.presentation.main.fragments.weather.WeatherFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mToolbar: Toolbar
    lateinit var mTitle: TextView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mNavView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        mTitle = findViewById(R.id.title_home)

        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, mDrawerLayout, mToolbar, 0, 0
        )

        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mNavView.setNavigationItemSelectedListener(this)

        startDafaultFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_home -> {
                mTitle.text = getString(R.string.app_name)
                fragment =
                    HomeFragment()
            }
            R.id.nav_weather -> {
                mTitle.text = getString(R.string.drawer_title_weather)
                fragment =
                    WeatherFragment()
            }
            R.id.nav_finance -> {
                mTitle.text = getString(R.string.drawer_title_finance)
                fragment =
                    FinanceFragment()
            }
            R.id.nav_geo -> {
                mTitle.text = getString(R.string.drawer_title_geo)
                fragment =
                    GeoFragment()
            }
            R.id.nav_api -> {
                mTitle.text = getString(R.string.drawer_title_api)
                fragment =
                    ApiFragment()
            }
            R.id.nav_about -> {
                mTitle.text = getString(R.string.drawer_title_about)
                fragment =
                    AboutFragment()
            }
        }

        val fragmentManaget = supportFragmentManager

        if (fragment != null) {
            fragmentManaget.beginTransaction().replace(R.id.frameContent, fragment).commit()
        }

        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun startDafaultFragment() {
        val fragment: Fragment =
            HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }
}