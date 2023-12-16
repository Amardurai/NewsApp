package com.example.androidcleanarchitecture.presentation.newsdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidcleanarchitecture.domain.model.News
import com.example.androidcleanarchitecture.domain.usecase.GetNewsDetailsUseCase
import com.example.androidcleanarchitecture.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getNewsDetailsUseCase: GetNewsDetailsUseCase) :BaseViewModel() {

    private val _showSmartMode = MutableLiveData(true)
    val showSmartMode: LiveData<Boolean> = _showSmartMode
    private val _news = MutableLiveData<News>()
    val news: LiveData<News> = _news

    fun getNewsById(id:Int){
        doAnAPICall({
            getNewsDetailsUseCase.getNewsDetails(id)
        }, onSuccess = {
            _news.value = it
        })
    }
    fun toggleMode() {
        _showSmartMode.value = !_showSmartMode.value!!
    }
}