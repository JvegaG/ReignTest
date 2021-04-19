package com.example.reigntest.Service

import com.google.gson.annotations.SerializedName

data class HackerNews(
        @SerializedName("hits") var hits: ArrayList<HitNews>,
        @SerializedName("nbHits") var nbHits: Int,
        @SerializedName("page") var page: Int,
        @SerializedName("nbPAges") var nbPages: Int,
        @SerializedName("hitsPerPage") var hitsPerPAges: Int,
        @SerializedName("exhaustiveNbHits") var exhaustiveNbHits: Boolean,
        @SerializedName("query") var query: String,
        @SerializedName("params") var params: String,
        @SerializedName("processingTimeMS") var processingTimeMS: Int
) {
}