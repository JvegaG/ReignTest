package com.example.reigntest.Service

import com.google.gson.annotations.SerializedName

data class HitNews(
        @SerializedName("created_at") var created_at: String?,
        @SerializedName("title") var title: String?,
        @SerializedName("url") var url: String?,
        @SerializedName("author") var author: String?,
        @SerializedName("points") var points: String?,
        @SerializedName("story_text") var story_text: String?,
        @SerializedName("comment_text") var comment_text: String?,
        @SerializedName("num_comments") var num_comments: String?,
        @SerializedName("story_id") var story_id: Int,
        @SerializedName("story_title") var story_title: String,
        @SerializedName("story_url") var story_url: String,
        @SerializedName("parent_id") var parent_id: Int,
        @SerializedName("_tags") var _tags: List<String?>?,
        @SerializedName("objectID") var objectID: String,
        @SerializedName("_highlightResult") var _highlightResult: HighlightClass
) {

}
