package com.example.dailydose

import android.util.Log
import com.example.dailydose.ui.main.NewsItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.ParseException

object ApiUtils {
    private val LOG_TAG: String = ApiUtils::class.java.simpleName

    suspend fun fetchNewsData(requestUrl: String): ArrayList<NewsItemModel> {
        var data: ArrayList<NewsItemModel>
        withContext(Dispatchers.IO) {
            val url = createUrl(requestUrl)
            var jsonResponse = ""
            try {
                jsonResponse = makeHttpRequest(url)
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Problem making HTTP request.", e)
            }
            data = extractDataFromJson(jsonResponse)
        }
        return data
    }

    private fun createUrl(urlString: String): URL? {
        var url: URL? = null
        try {
            url = URL(urlString)
        } catch (e: MalformedURLException) {
            Log.e(LOG_TAG, "Problem building the URL.", e)
        }
        return url
    }

    @Throws(IOException::class)
    private fun makeHttpRequest(url: URL?): String {
        var jsonResponse = ""
        if (url == null) {
            return jsonResponse
        }
        var urlConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.readTimeout = 10000
            urlConnection.connectTimeout = 15000
            urlConnection.requestMethod = "GET"
            urlConnection.connect()
            if (urlConnection.responseCode == 200) {
                inputStream = urlConnection.inputStream
                jsonResponse = readFromStream(inputStream)
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.responseCode)
            }
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e)
        } finally {
            urlConnection?.disconnect()
            inputStream?.close()
        }
        return jsonResponse
    }

    @Throws(IOException::class)
    private fun readFromStream(inputStream: InputStream?): String {
        val output = StringBuilder()
        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line = bufferedReader.readLine()
            while (line != null) {
                output.append(line)
                line = bufferedReader.readLine()
            }
        }
        return output.toString()
    }

    private fun extractDataFromJson(newsResponseJson: String): ArrayList<NewsItemModel> {
        if (newsResponseJson.isEmpty()) {
            return ArrayList()
        }
        val newsItems = ArrayList<NewsItemModel>()
        try {
            val response = JSONObject(newsResponseJson).getJSONObject("response")
            val newsItemsArray = response.getJSONArray("results")
            for (i in 0 until newsItemsArray.length()) {
                val currNewsObject = newsItemsArray.getJSONObject(i)
                val title = currNewsObject.getString("webTitle")
                val webURL = currNewsObject.getString("webUrl")
                var imageUrl = ""
                if(currNewsObject.has("fields")) {
                    imageUrl = currNewsObject.getJSONObject("fields").getString("thumbnail")
                }
                newsItems.add(NewsItemModel(title, webURL, imageUrl))
            }
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results.", e)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return newsItems
    }
}