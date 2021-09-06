package com.example.yournews.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yournews.data.model.News
import com.example.yournews.R

class NewsListAdapter(private val listener: NewsItemClicked, private val shareListener: ShareButtonClicked): RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val itemList = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, listener, shareListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateNews(updateNewsList: List<News>?) {
        itemList.clear()
        if (updateNewsList != null) {
            itemList.addAll(updateNewsList)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val author: TextView = itemView.findViewById(R.id.tv_author)
        private val image: ImageView = itemView.findViewById(R.id.iv_image)
        private val share: ImageView = itemView.findViewById(R.id.share_button)

        fun bind(itemList: News, clickListener: NewsItemClicked, shareListener: ShareButtonClicked) {
            title.text = itemList.title
            author.text = itemList.author ?: "Not Known"
            Glide.with(itemView.context).load(itemList.urlToImage).into(image)
            share.setOnClickListener {
                shareListener.onItemClicked(itemList.url!!)
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(itemList)
            }
        }
    }

    interface NewsItemClicked {
        fun onItemClicked(item: News)
    }

    interface ShareButtonClicked {
        fun onItemClicked(url: String)
    }
}