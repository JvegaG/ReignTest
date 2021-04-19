package com.example.reigntest.Service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceHackNews {
    @GET("search_by_date")
    suspend fun getData(
        @Query ("query") value: String
    ): HackerNews
}