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

    suspend fun getCountryData(api: String) : CaseModel = withContext(Dispatchers.IO) {
        extractCountryData(getResponse(api))
    }

    suspend fun getStateCases(api: String) : Map<String, CaseModel> = withContext(Dispatchers.IO) {
        extractSateData(getResponse(api))
    }

    private fun getResponse(api: String) : String {
        val url = ApiUtils.createUrl(api)
        var jsonResponse = ""
        try {
            jsonResponse = ApiUtils.makeHttpRequest(url)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Problem making HTTP request.", e)
        }
        return jsonResponse
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

    private fun extractSateData(response: String) : Map<String, CaseModel> {
        val cases = mutableMapOf<String, CaseModel>()
        if(response.isEmpty())
            return cases

        try {
            val data = JSONObject(response).getJSONObject("data")
            val states = data.getJSONArray("statewise")
            for(i in 0 until states.length()) {
                val currState = states[i] as JSONObject
                val stateName = currState.getString("state")
                val activeTotal = currState.getInt("active")
                val recoveredTotal = currState.getInt("recovered")
                val deathsTotal = currState.getInt("deaths")
                cases[stateName] = CaseModel(activeTotal, recoveredTotal, deathsTotal)
            }
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results.", e)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return cases
    }
}
