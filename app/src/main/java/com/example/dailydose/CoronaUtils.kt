package com.example.dailydose

import android.util.Log
import com.example.dailydose.ui.main.CaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.ParseException

object CoronaUtils {
    private val LOG_TAG: String = CoronaUtils::class.java.simpleName

    suspend  fun fetchCases(countryUrl: String) = withContext(Dispatchers.IO) {
        val url = ApiUtils.createUrl(countryUrl)
        var jsonResponse = ""
        try {
            jsonResponse = ApiUtils.makeHttpRequest(url)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Problem making HTTP request.", e)
        }
        extractCountryData(jsonResponse)
    }

    private fun extractCountryData(jsonResponse: String): CaseModel {
        val cases = CaseModel()
        if (jsonResponse.isEmpty())
            return cases

        try {
            val data = JSONObject(jsonResponse)

            cases.activeTotal = data.getInt("active")
            cases.activeToday = data.getInt("todayCases")

            cases.deathsTotal = data.getInt("deaths")
            cases.activeToday = data.getInt("todayDeaths")

            cases.recoveredTotal = data.getInt("recovered")
            cases.recoveredToday = data.getInt("todayRecovered")
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results.", e)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return cases
    }
}
