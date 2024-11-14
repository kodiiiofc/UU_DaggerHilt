package com.kodiiiofc.urbanuniversity.daggerhilt.presetation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kodiiiofc.urbanuniversity.daggerhilt.databinding.ActivityMainBinding
import com.kodiiiofc.urbanuniversity.daggerhilt.domain.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainVewModel>()
    private lateinit var newsAdapter: NewsAdapter

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.recyclerView.adapter = newsAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()

        viewModel.newsLiveData.observe(this) { response ->
            when (response) {
                is Resources.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resources.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.d("aaa", "onCreate: ${response.data}")
                }
            }
        }
    }


}