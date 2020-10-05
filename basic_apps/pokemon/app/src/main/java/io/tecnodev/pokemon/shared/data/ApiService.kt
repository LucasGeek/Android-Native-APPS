package io.tecnodev.pokemon.shared.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.tecnodev.pokemon.shared.constant.ConstantApp
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {

    private fun initRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(ConstantApp.SERVICE.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
    }

    val service: PokemonServices = initRetrofit().create(PokemonServices::class.java)
}