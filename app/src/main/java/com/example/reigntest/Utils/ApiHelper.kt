package com.example.reigntest.Utils

import com.example.reigntest.Service.ServiceHackNews

class ApiHelper(private val api: ServiceHackNews) {

    suspend fun getHits() = api.getData("mobile").hits
}