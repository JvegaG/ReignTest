package com.example.reigntest.Utils

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getDataHits() = apiHelper.getHits()
}