package gg.bidavid.tvshowsapp.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.tvmaze.com/"

object NetworkUtils {

    val apiInterface:APIinterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIinterface::class.java)

}