package com.example.taskschedule.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.taskschedule.R
import com.example.taskschedule.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()

        mUserBusiness = UserBusiness(this)

        verifyLogged()
    }

    private fun verifyLogged() {
        if(mUserBusiness.verifyLogged()) {
            goToHome()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                handleLogin()
            }
            R.id.btnRegister -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if (mUserBusiness.login(email, password)) {
            goToHome()
        } else {
            Toast.makeText(this, getString(R.string.usuario_senha_incorretos), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun setListeners() {
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    private fun goToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
