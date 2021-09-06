package com.example.yournews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yournews.data.api.APIClient
import com.example.yournews.data.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel : ViewModel() {

    private val newsLiveData: MutableLiveData<List<News>> by lazy {
        MutableLiveData<List<News>>().also {
            fetchData()
        }
    }

    fun getNewsLiveData(): LiveData<List<News>> {
        return newsLiveData
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                APIClient.api.getNews()
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    it.articles?.let { newsLiveData.postValue(it) }
                }
            }
        }
    }
}