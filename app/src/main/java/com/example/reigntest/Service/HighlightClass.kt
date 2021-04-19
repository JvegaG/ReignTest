package com.example.reigntest.Service

import com.google.gson.annotations.SerializedName

data class HighlightClass(
    @SerializedName("author") var author: FinalClass,
    @SerializedName("comment_text") var comment_text: CommentClass,
    @SerializedName("story_title") var story_title: FinalClass,
    @SerializedName("story_url") var story_url: FinalClass
) {
}

data class CommentClass(
    @SerializedName("value") var value: String?,
    @SerializedName("matchLevel") var matchLevel: String?,
    @SerializedName("fullyHighlighted") var fullyHighlighted: Boolean,
    @SerializedName("matchedWords") var matchedWords: List<String?>?
) {
}

data class FinalClass(
    @SerializedName("value") var value: String?,
    @SerializedName("matchLevel") var matchLevel: String?,
    @SerializedName("matchedWords") var matchedWords: List<String?>?
) {
}
