package gg.bidavid.tvshowsapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShowPoster(
    @SerializedName("medium") val mediumURL: String,
    @SerializedName("original") val originalURL: String
    ):Serializable