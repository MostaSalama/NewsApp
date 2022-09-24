package com.example.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Model.*
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.newsAdapter
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment:Fragment() {
    lateinit var tabLayout : TabLayout
    lateinit var progressBar : ProgressBar
    lateinit var recyclerView: RecyclerView

    companion object{
        fun getInstance(category: Category):NewsFragment{
                val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }
    lateinit var category: Category

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getNewsSources()

    }

    override fun onResume() {
        super.onResume()
        getNewsSources()
    }


    val adapter = newsAdapter(null)
    fun initViews(){
        tabLayout = requireView().findViewById(R.id.tabLayout)
        progressBar =  requireView().findViewById(R.id.progress_bar)
        recyclerView =requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
    }

    private fun getNewsSources() {
        ApiManager.getApis()
            .getSources(Constant.apiKey,category.id)
            .enqueue(object :Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>,
                ) {

                    progressBar.isVisible =false
                    addSourcesToTabLayout(response.body()?.sources)
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e("error",t.message.toString())
                }
            })
    }

    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach{
            val tab = tabLayout.newTab()
            tab.setText(it?.name)
            tab.tag = it
            tabLayout.addTab(tab)

        }
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
//                            val source = sources?.get(tab?.position?:0)
                    val source = tab?.tag as SourcesItem
                    getNewsBySource(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getNewsBySource(source)
                }
            }
        )
        tabLayout.getTabAt(0)?.select()
    }

    private fun getNewsBySource(source: SourcesItem) {
        progressBar.isVisible = true
        ApiManager.getApis().getNews(Constant.apiKey,source.id?:"")
            .enqueue(object :Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>,
                ) {
                    progressBar.isVisible = false
                    adapter.changeData(response.body()?.articles)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.isVisible = false

                }
            })
    }
}