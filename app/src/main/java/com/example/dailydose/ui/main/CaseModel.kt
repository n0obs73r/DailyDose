package com.example.dailydose.ui.main

data class CaseModel(
    var activeTotal: Int = 0,
    var deathsTotal: Int = 0,
    var recoveredTotal: Int = 0,
    var activeToday: Int = 0,
    var deathsToday: Int = 0,
    var recoveredToday: Int = 0
    )