package com.example.androidcleanarchitecture.presentation.newslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidcleanarchitecture.domain.model.News
import com.example.androidcleanarchitecture.domain.usecase.GetNewsListUseCase
import com.example.androidcleanarchitecture.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCase: GetNewsListUseCase
) : BaseViewModel() {
    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    fun getNewsList() {
        doAnAPICall({
            useCase.getNewsList()
        }, onSuccess = {
            _newsList.postValue(it)
        }
        )
    }
}