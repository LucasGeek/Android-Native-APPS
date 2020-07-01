package com.example.hgbrasil.presentation.permission

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.hgbrasil.R
import com.example.hgbrasil.presentation.splash.SplashActivity
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mPermissionsOption: QuickPermissionsOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        mPermissionsOption = QuickPermissionsOptions(
            handleRationale = false,
            rationaleMessage = getString(R.string.denied_message_permission),
            permanentlyDeniedMessage = getString(R.string.permanently_denied_message_permission),
            rationaleMethod = { rationaleCallback(it) },
            permanentDeniedMethod = { permissionsPermanentlyDenied(it) },
            permissionsDeniedMethod = { whenPermAreDenied(it) }
        )

        btnPermission.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPermission -> methodWithPermissions()
        }
    }

    fun methodWithPermissions() = runWithPermissions(
        Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION, options = mPermissionsOption)
    {
        changeSplashScreenActvity()
    }

    private fun changeSplashScreenActvity() {
        val intent: Intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun rationaleCallback(req: QuickPermissionsRequest) {
        // this will be called when permission is denied once or more time. Handle it your way
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_denied_message_permission))
            .setMessage(getString(R.string.denied_message_permission))
            .setPositiveButton("OK") { dialog, which -> req.proceed() }
            .setNegativeButton("Cancelar") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

    private fun permissionsPermanentlyDenied(req: QuickPermissionsRequest) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_denied_message_permission))
            .setMessage(getString(R.string.permanently_denied_message_permission))
            .setPositiveButton("Configuração") { dialog, which -> req.openAppSettings() }
            .setNegativeButton("Cancelar") { dialog, which -> req.cancel() }
            .setCancelable(false)
            .show()
    }

    private fun whenPermAreDenied(req: QuickPermissionsRequest) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_denied_message_permission))
            .setMessage(getString(R.string.denied_message_permission))
            .setPositiveButton("Tudo bem!") { _, _ -> }
            .setCancelable(false)
            .show()
    }
}
