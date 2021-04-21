package com.example.dailydose.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoronaViewModel : ViewModel() {
    public var activeCasesCountry = MutableLiveData<Int>()
    public var deathsCountry = MutableLiveData<Int>()
    var recoveredCountry = MutableLiveData<Int>()

    var activeCasesState = MutableLiveData<Int>()
    var deathsState = MutableLiveData<Int>()
    var recoveredState = MutableLiveData<Int>()

    var activeCasesDistrict = MutableLiveData<Int>()
    var deathsDistrict = MutableLiveData<Int>()
    var recoveredDistrict = MutableLiveData<Int>()
}