package com.example.mychittor.main.ui

import android.os.Build
import android.os.PersistableBundle.readFromStream
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.mychittor.NewsData
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
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
        return jsonResponse?.let { extractData(it) }
    }

    private fun extractData(newsJson: String?): ArrayList<NewsData?>? {

        if(newsJson.isNullOrEmpty())
            return null

        val newsItems = ArrayList<NewsData?>()
        try {
            val newsItemArray = JSONObject(newsJson).getJSONArray("articles")
            for(i in 0 until newsItemArray.length()) {
                val currNewsObject = newsItemArray.getJSONObject(i)
                val title = currNewsObject.getString("title")
                val link = currNewsObject.getString("url")
                newsItems.add(NewsData(title, link))
            }
        }catch(e : Exception) {}
        return newsItems
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
        if(inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream)
            val buffered = BufferedReader(inputStreamReader)
            var line = buffered.readLine()
            while(line != null){
                output.append(line)
                line = buffered.readLine()
            }
        }

        return output.toString()

    }

    }
