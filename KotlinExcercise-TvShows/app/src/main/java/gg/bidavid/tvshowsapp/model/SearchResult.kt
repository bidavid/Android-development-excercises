package gg.bidavid.tvshowsapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResult(
    @SerializedName("score") val score: Double,
    @SerializedName("show") val show: TvShow
):Serializable