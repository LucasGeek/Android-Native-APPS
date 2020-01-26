package com.example.motivationgenerator.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.motivationgenerator.R
import com.example.motivationgenerator.R.drawable.*
import com.example.motivationgenerator.mock.Mock
import com.example.motivationgenerator.util.MotivationConstants
import com.example.motivationgenerator.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mFilter = MotivationConstants.PHRASE_FILTER.ALL
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mMock = Mock()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSecurityPreferences = SecurityPreferences(this)

        setListeners()

        verifyUserName()
        handleFilter(R.id.imageAll)
        refreshPhrase()
    }

    override fun onClick(v: View) {
        val listId = listOf(R.id.imageAll, R.id.imageSun, R.id.imageHappy)
        if (v.id in listId) {
            handleFilter(v.id)
        } else if (v.id == R.id.btnNewOnPhrase) {
            refreshPhrase()
        }
    }

    private fun refreshPhrase() {
        textUserPhrase.text = mMock.getPhrase(mFilter)
    }

    private fun handleFilter(id: Int) {
        imageHappy.setImageResource(ic_happy_unselected)
        imageSun.setImageResource(ic_sun_unselected)
        imageAll.setImageResource(ic_all_unselected)

        mFilter = when (id) {
            R.id.imageAll -> {
                imageAll.setImageResource(ic_all_selected)
                MotivationConstants.PHRASE_FILTER.ALL
            }
            R.id.imageSun -> {
                imageSun.setImageResource(ic_sun_selected)
                MotivationConstants.PHRASE_FILTER.MORNING
            }
            else -> {
                imageHappy.setImageResource(ic_happy_selected)
                MotivationConstants.PHRASE_FILTER.HAPPY
            }
        }
    }

    private fun setListeners() {
        imageAll.setOnClickListener(this)
        imageHappy.setOnClickListener(this)
        imageSun.setOnClickListener(this)
        btnNewOnPhrase.setOnClickListener(this)
    }

    private fun verifyUserName() {
        textUserName.text = mSecurityPreferences.getStoredString(MotivationConstants.KEY.PERSON_NAME)
    }
}