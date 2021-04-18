package com.example.mychittor.ui.main

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychittor.R
import com.squareup.picasso.Picasso

class NewsItemAdapter(private val newsItems: ArrayList<NewsItemModel>)
    : RecyclerView.Adapter<NewsItemAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.news_title)
        val linkButton: Button = view.findViewById(R.id.news_link)

        fun bindTo(newsItem : NewsItemModel) {
            titleTextView.text = newsItem.title
            Picasso.get().load(newsItem.imageUrl).into(thumbnail);
            linkButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.link))
                linkButton.context.startActivity(intent)
            }

        }
        val thumbnail : ImageView = view.findViewById(R.id.news_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindTo(newsItems.get(position))
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    fun update(newData: ArrayList<NewsItemModel>) {
        newsItems.clear()
        newsItems.addAll(newData)
        notifyDataSetChanged()
    }
}