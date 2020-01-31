package com.example.taskschedule.views

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.taskschedule.R
import com.example.taskschedule.business.PriorityBusiness
import com.example.taskschedule.business.UserBusiness
import com.example.taskschedule.constants.TaskConstants
import com.example.taskschedule.repository.PriorityCacheConstants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mPriorityBusiness: PriorityBusiness

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.app_name, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mUserBusiness = UserBusiness(this)
        mPriorityBusiness = PriorityBusiness(this)

        loadPriorityCache()
        startDafaultFragment()
        setListeners()
        formatUserName()
        formatDate()
    }

    private fun loadPriorityCache() {
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    private fun startDafaultFragment() {
        val fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.COMPLETE)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun formatUserName() {
        textHello.text = "Olá, ${mUserBusiness.getNameUser()}!"

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView.getHeaderView(0)

        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)

        name.text = mUserBusiness.getNameUser()
        email.text = mUserBusiness.getEmailUser()
    }

    private fun formatDate() {
        val calendar = Calendar.getInstance()

        val days = arrayOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado")
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novemembro", "Dezembro")

        val str = "${days[calendar.get(Calendar.DAY_OF_WEEK) - 1]}, ${calendar.get(Calendar.DAY_OF_MONTH)} de ${months[calendar.get(Calendar.MONTH)]}"
        textDateDescription.text = str
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_done -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.COMPLETE)
            }
            R.id.nav_todo -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASK_FILTER.TODO)
            }
            R.id.nav_logout -> {
                handleLogout()
                return true
            }
        }

        val fragmentManaget = supportFragmentManager

        if (fragment != null) {
            fragmentManaget.beginTransaction().replace(R.id.frameContent, fragment).commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setListeners() {
        navView.setNavigationItemSelectedListener(this)
    }

    private fun handleLogout() {
        mUserBusiness.doLogout()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
