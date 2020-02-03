package com.example.taskschedule.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.taskschedule.R
import com.example.taskschedule.business.UserBusiness
import com.example.taskschedule.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Eventos
        setListerners()

        //Instanciar variaveis da classe
        mUserBusiness = UserBusiness(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnRegister -> {
                handleRegister()
            }
        }
    }

    private fun handleRegister() {
        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            mUserBusiness.Insert(name, email, password)

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado) + " " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setListerners() {
        btnRegister.setOnClickListener(this)
    }
}
