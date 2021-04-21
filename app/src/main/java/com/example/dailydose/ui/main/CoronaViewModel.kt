package com.example.dailydose.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailydose.CoronaUtils
import kotlinx.coroutines.launch

class CoronaViewModel : ViewModel() {
    private val apiCountryUrl = "https://corona.lmao.ninja/v3/covid-19/countries/"
    val countryCases = MutableLiveData<CaseModel>()

    fun getCountryCases(country: String) {
        viewModelScope.launch {
            countryCases.postValue(CoronaUtils.fetchCases(apiCountryUrl + country))
        }
    }
}