package com.example.hgbrasil.shared.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiService {
    val service : HGServices by lazy {
        Log.d("ApiService", "Creating retrofit client")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hgbrasil.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return@lazy retrofit.create(HGServices::class.java)
    }
}