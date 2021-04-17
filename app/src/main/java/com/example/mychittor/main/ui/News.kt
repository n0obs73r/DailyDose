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


class News : Fragment() {
    private lateinit var newsListAdapter: ItemAdapter
    val API_URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=f0ffb8c0678d4bc5b3dc8f507c07450d"
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_news, container, false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        newsListAdapter = ItemAdapter(context, ArrayList())
        recyclerView.adapter = newsListAdapter

        // Inflate the layout for this fragment
        GlobalScope.launch(Dispatchers.IO) {
            recyclerView.adapter.items = getData()
        }
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getData() : ArrayList<NewsData?>? {
        return NewsUtils.fetchNewsData(API_URL)
    }

}