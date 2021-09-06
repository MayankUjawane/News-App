package com.example.yournews.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yournews.R
import com.example.yournews.data.model.News
import com.example.yournews.databinding.ActivityMainBinding
import com.example.yournews.ui.adapter.NewsListAdapter
import com.example.yournews.ui.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity(), NewsListAdapter.NewsItemClicked,
    NewsListAdapter.ShareButtonClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: NewsListAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNews.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsListAdapter(this, this)
        binding.rvNews.adapter = mAdapter

        newsViewModel.getNewsLiveData().observe(this, {
            mAdapter.updateNews(it)
        })

    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val colorInt = ContextCompat.getColor(this, R.color.purple_500)
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build()
        builder.setDefaultColorSchemeParams(defaultColors)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    override fun onItemClicked(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Checkout this latest news $url")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }
}