package com.example.yournews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yournews.model.News
import com.example.yournews.R

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val itemList = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, listener)
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

        fun bind(itemList: News, clickListener: NewsItemClicked) {
            title.text = itemList.title
            author.text = itemList.author ?: "Not Known"
            Glide.with(itemView.context).load(itemList.urlToImage).into(image)

            itemView.setOnClickListener {
                clickListener.onItemClicked(itemList)
            }
        }
    }

    interface NewsItemClicked {
        fun onItemClicked(item: News)
    }
}