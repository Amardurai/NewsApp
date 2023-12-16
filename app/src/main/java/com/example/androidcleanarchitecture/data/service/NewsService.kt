package com.example.androidcleanarchitecture.data.service

import com.example.androidcleanarchitecture.data.model.response.NewsResponse
import com.example.androidcleanarchitecture.utils.Constants.DEFAULT_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") q: String = "bitcoin",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE
    ): Response<NewsResponse>
}