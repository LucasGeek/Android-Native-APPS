package com.example.motivationgenerator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.motivationgenerator.R
import com.example.motivationgenerator.util.MotivationConstants
import com.example.motivationgenerator.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSecurity: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSecurity =
            SecurityPreferences(this)

        btnSave.setOnClickListener(this)
        verifyUserName()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSave -> handleSave()
        }
    }

    private fun handleSave() {
        val name: String = editName.text.toString()

        if (name.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.informe_seu_nome), Toast.LENGTH_LONG).show()
        } else {
            mSecurity.storeString(MotivationConstants.KEY.PERSON_NAME, name)

            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

    }

    private fun verifyUserName() {
        val userName = mSecurity.getStoredString(MotivationConstants.KEY.PERSON_NAME)

        if(userName.isNotEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
        }

       editName.setText(userName)
    }
}
