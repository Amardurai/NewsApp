package com.example.androidcleanarchitecture.data.repository

import com.example.androidcleanarchitecture.data.model.request.RequestException
import com.example.androidcleanarchitecture.data.service.NewsService
import com.example.androidcleanarchitecture.domain.repository.NewsRepository
import com.example.androidcleanarchitecture.domain.model.News
import java.net.HttpURLConnection
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsService: NewsService) :
    NewsRepository {
    private val cachedList: MutableList<News> = mutableListOf()

    //Used to fetch NewsList response override from todo NewsRepository class
    override suspend fun getNewsList(): Result<List<News>> {
        val apiResponse = newsService.getNews().body()
        if (apiResponse?.status.equals("ok")) {
            val newsList = apiResponse?.articles ?: emptyList()
            cachedList.addAll(newsList)
            return Result.success(newsList)
        }
        return Result.failure(
            RequestException(
                code = HttpURLConnection.HTTP_INTERNAL_ERROR,
                message = "An error occurred!"
            )
        )
    }

    //Used to fetch getNewsDetail response
    override suspend fun getNewsDetail(id: Int): Result<News> {
        return cachedList.find { it.id == id }?.let { news ->
            Result.success(news)
        } ?: run {
            Result.failure(Exception("An error occurred when get new detail"))
        }
    }
}