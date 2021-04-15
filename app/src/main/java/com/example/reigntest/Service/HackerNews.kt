package com.example.reigntest.Service

class HackerNews(
        val hits: ArrayList<HitNews>,
        val nbHits: Int,
        val page: Int,
        val nbPages: Int,
        val histsPerPAges: Int,
        val exhaustiveNbHits: Boolean,
        val query: String,
        val params: String,
        val ProcessingTimeMS: Int
) {
}