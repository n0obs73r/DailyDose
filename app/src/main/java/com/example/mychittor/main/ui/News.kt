import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mychittor.R
import androidx.annotation.RequiresApi
import com.example.mychittor.NewsData
import com.example.mychittor.main.ui.NewsUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import okhttp3.Dispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread


class News : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsItemAdapter : ItemAdapter
    val API_URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=f0ffb8c0678d4bc5b3dc8f507c07450d"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerView = inflater.inflate(R.layout.fragment_news, container, false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        newsItemAdapter = ItemAdapter(context, ArrayList())
        recyclerView.adapter = newsItemAdapter
        return recyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thread {
            newsItemAdapter.updateData(getData())
        }
    }

    private fun getData() : ArrayList<NewsData?>? =
        NewsUtils.fetchNewsData(API_URL)
}