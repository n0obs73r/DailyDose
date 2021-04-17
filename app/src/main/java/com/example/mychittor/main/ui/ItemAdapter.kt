import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mychittor.NewsData
import com.example.mychittor.R


class ItemAdapter(context: Context?, items: ArrayList<NewsData?>?) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var context: Context?
    var items: ArrayList<NewsData?>?
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
        holder.mybutton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val uri : Uri = Uri.parse(url)
                context?.startActivity(Intent(Intent.ACTION_VIEW, uri))

            }
        })
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editTitle: TextView
        var mybutton: Button

        init {
            editTitle = itemView.findViewById(R.id.tittle1)
            mybutton = itemView.findViewById(R.id.details)
        }
    }

    init {
        this.context = context
        this.items = items!!
    }
}