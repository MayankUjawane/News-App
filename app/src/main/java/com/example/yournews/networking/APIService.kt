package com.example.yournews.networking

import com.example.yournews.model.NewsData
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("top-headlines?country=us&apiKey=110b7224e46b43b3a2ec5581617ad404")
    suspend fun getNews(): Response<NewsData>

}