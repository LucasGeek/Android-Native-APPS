package com.example.hgbrasil.presentation.main.fragments.home

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hgbrasil.R
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions


class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mContext: Context

    private var locationManager : LocationManager? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var latTextView: TextView? = null
    private var lonTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        mContext = rootView.context

        rootView.findViewById<FloatingActionButton>(R.id.floatRefresh).setOnClickListener(this)

        latTextView = rootView.findViewById(R.id.latTextView)
        lonTextView = rootView.findViewById(R.id.lonTextView)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager?

        loadFragment()

        return rootView
    }

    override fun onResume() {
        super.onResume()

        loadFragment()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.floatRefresh -> {
                loadFragment()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun loadFragment() = runWithPermissions(
        Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    {
        if (isLocationEnabled()) {
            mFusedLocationClient!!.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        latTextView!!.text = (location.latitude).toString()
                        lonTextView!!.text = (location.longitude).toString()
                    }
                }
        } else {
            Toast.makeText(mContext, "Ative sua localização!", Toast.LENGTH_LONG).show()
        }
    }
}