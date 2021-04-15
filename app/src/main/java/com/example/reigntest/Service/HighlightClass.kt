package com.example.reigntest.Service

class HighlightClass(
    val author: FinalClass,
    val comment_text: CommentClass,
    val story_title: FinalClass,
    val story_url: FinalClass
    
) {

}

class CommentClass(
    val value: String?,
    val matchLevel: String?,
    val fullyHighlighted: Boolean,
    val matchedWords: List<String?>?
) {
}

class FinalClass(
    val value: String?,
    val matchLevel: String?,
    val matchedWords: List<String?>?
) {
}
