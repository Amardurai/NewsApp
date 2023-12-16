package com.example.androidcleanarchitecture.domain.usecase

import com.example.androidcleanarchitecture.domain.model.News
import com.example.androidcleanarchitecture.domain.repository.NewsRepository
import javax.inject.Inject



class GetNewsDetailsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun getNewsDetails(id: Int): Result<News> {
        return newsRepository.getNewsDetail(id)
    }
}