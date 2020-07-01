package com.example.hgbrasil.presentation.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hgbrasil.R
import com.example.hgbrasil.presentation.main.MainActivity
import com.example.hgbrasil.presentation.permission.PermissionActivity
import com.example.hgbrasil.shared.util.DotProgressBar
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*
import kotlin.collections.ArrayList


class SplashActivity : AppCompatActivity() {
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val dotProgressBar = DotProgressBar.Builder()
            .setMargin(1)
            .setAnimationDuration(1000)
            .setDotBackground(R.drawable.ic_dot_primary)
            .setMaxScale(0.8f)
            .setMinScale(0.3f)
            .setNumberOfDots(3)
            .setdotRadius(8)
            .build(this)
        frame_layout.addView(dotProgressBar)
        dotProgressBar.startAnimation()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                initAplication()
            }
        }, 2000)
    }

    private fun initAplication() {
        if(!checkAndRequestPermissions()) {
            changePermissionsActvity()
        } else {
            changeMainActvity()
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        val readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val locationFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val locationCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val locationBackground = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET)
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (locationFine != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (locationCoarse != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (locationBackground != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }

        return true
    }

    private fun changePermissionsActvity() {
        val intent: Intent = Intent(this, PermissionActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun changeMainActvity() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }
}
