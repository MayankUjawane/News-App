package com.example.yournews

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yournews.adapter.NewsListAdapter
import com.example.yournews.databinding.ActivityMainBinding
import com.example.yournews.model.News
import com.example.yournews.networking.APIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), NewsListAdapter.NewsItemClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNews.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        binding.rvNews.adapter = mAdapter
    }

    private fun fetchData() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                APIClient.api.getNews()
            }

            if (response.isSuccessful) {
                response.body()?.let {
                    mAdapter.updateNews(it.articles)
                }
            }
        }
    }

    override fun onItemClicked(item: News) {
        val url = item.url
        val builder = CustomTabsIntent.Builder();
        val colorInt = ContextCompat.getColor(this, R.color.purple_500)
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build();
        builder.setDefaultColorSchemeParams(defaultColors);
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}