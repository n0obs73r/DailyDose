import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychittor.NewsData
import com.example.mychittor.R


class ItemAdapter(private val context: Context?, val  items: ArrayList<NewsData?>?) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.news_items, null) as LinearLayout
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val item: NewsData? = items!![i]
        val title: String? = item!!.mTitle
        val url: String? = item.mWebURL
        holder.editTitle.text = title
        holder.mybutton.setOnClickListener {
            val uri: Uri = Uri.parse(url)
            context?.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editTitle: TextView = itemView.findViewById(R.id.tittle1)
         var mybutton: Button = itemView.findViewById(R.id.details)
     }

    fun updateData(newItems : ArrayList<NewsData?>?) {
        items?.clear()
        if (newItems != null) {
            items?.addAll(newItems)
        }
    }
}