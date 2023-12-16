package com.example.androidcleanarchitecture.domain.repository

import com.example.androidcleanarchitecture.domain.model.News

// todo GetNewsListUseCase class interact with NewsRepository interface to implement "getNewsList"
interface NewsRepository {
    suspend fun getNewsList(): Result<List<News>>

    suspend fun getNewsDetail(id: Int): Result<News>
}