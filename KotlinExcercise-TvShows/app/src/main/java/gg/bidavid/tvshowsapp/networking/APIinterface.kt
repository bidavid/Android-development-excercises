package gg.bidavid.tvshowsapp.networking

import gg.bidavid.tvshowsapp.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIinterface {
    @GET("search/shows")
    fun getTvShows(@Query("q") title:String ): Call<List<SearchResult>>
}