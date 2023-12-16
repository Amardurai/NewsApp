package com.example.androidcleanarchitecture.domain.usecase

import com.example.androidcleanarchitecture.domain.model.News
import com.example.androidcleanarchitecture.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    suspend fun getNewsList(): Result<List<News>> {

        return newsRepository.getNewsList()
    }
}