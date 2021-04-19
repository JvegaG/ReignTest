package com.example.reigntest.Utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DataViewModel(private val repository: Repository) : ViewModel() {
    fun getInfoModel() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = repository.getDataHits()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Ocurred!"))
        }

    }
}