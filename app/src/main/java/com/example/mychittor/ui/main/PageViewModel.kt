package com.example.mychittor.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mychittor.ApiUtils
import kotlinx.coroutines.launch

class PageViewModel : ViewModel() {

    private val apiUrl = "https://content.guardianapis.com/search?q=india&api-key=42b74d66-8cdf-453f-a2c5-32ec213f21f3&&show-fields=thumbnail&page-size=30"
    val newsItems = MutableLiveData<ArrayList<NewsItemModel>>()

    init {
        viewModelScope.launch {
            newsItems.value = ApiUtils.fetchNewsData(apiUrl)
        }
    }
}