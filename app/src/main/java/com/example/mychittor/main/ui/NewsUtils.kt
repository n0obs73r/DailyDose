package com.example.mychittor.main.ui

import android.os.Build
import android.os.PersistableBundle.readFromStream
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mychittor.NewsData
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.*

object NewsUtils {

    private val LOG_TAG = "NewsUtils::class"
    private fun createUrl(urlString: String?): URL? {
        var url: URL? = null
        try  {
            url = URL(urlString)
        } catch (e: MalformedURLException) {
            Log.e(LOG_TAG, "Problem building the URL.", e)
        }
        return url
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun fetchNewsData(requestUrl: String?): ArrayList<NewsData?>? {

        val url = createUrl(requestUrl)
        var jsonResponse : String? = null
        try {
            jsonResponse = makeRequest(url)
        } catch (e : IOException) {}
        return extractData(jsonResponse)
    }

    private fun extractData(requestUrl: String?): ArrayList<NewsData?>? {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun makeRequest(url: URL?): String? {
        var jsonResponse: String? = ""
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
                jsonResponse = readFromStream(inputStream).toString()
            }
        } catch (e: IOException) {
        } finally {
            urlConnection?.disconnect()
            inputStream?.close()
        }
        return jsonResponse
    }
    private fun  readFromInputStream(inputStream: InputStream?) : String?{

        val output = StringBuilder()

    }

    }
