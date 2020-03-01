package gg.bidavid.tvshowsapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TvShow(
    @SerializedName("name") val title: String,
    @SerializedName("premiered") val premiereDate: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("image") val poster: TvShowPoster
):Serializable