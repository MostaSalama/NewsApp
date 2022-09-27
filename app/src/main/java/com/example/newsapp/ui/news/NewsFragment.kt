package com.example.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.model.*
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.newsAdapter
import com.google.android.material.tabs.TabLayout

class NewsFragment:Fragment() {
//    lateinit var tabLayout : TabLayout
//    lateinit var progressBar : ProgressBar
//    lateinit var recyclerView: RecyclerView
    lateinit var viewModel:NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    companion object{
        fun getInstance(category: Category):NewsFragment{
                val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }
    lateinit var category: Category

    lateinit var viewDataBinding:FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return viewDataBinding.root
        viewDataBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_news,container,false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeToLiveData()
        viewModel.getNewsSources(category)

    }

    private fun subscribeToLiveData() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner
        ) {
            addSourcesToTabLayout(it)
        }
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            showNews(it)
        }
        viewModel.progressVisible.observe(viewLifecycleOwner){isVisible->
            viewDataBinding.progressBar.isVisible = isVisible
        }
        viewModel.messageLiveData.observe(viewLifecycleOwner){message->
            Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
        }


    }

    private fun showNews(newsList: List<ArticlesItem?>?) {
        adapter.changeData(newsList)
    }

    val adapter = newsAdapter(null)
    fun initViews(){
//        tabLayout = requireView().findViewById(R.id.tabLayout)
//        progressBar =  requireView().findViewById(R.id.progress_bar)
//        recyclerView =requireView().findViewById(R.id.recycler_view)
        viewDataBinding.recyclerView.adapter = adapter
    }



    private fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach{
            val tab = viewDataBinding.tabLayout.newTab()
            tab.setText(it?.name)
            tab.tag = it
            viewDataBinding.tabLayout.addTab(tab)

        }
        viewDataBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
//                            val source = sources?.get(tab?.position?:0)
                    val source = tab?.tag as SourcesItem
//                    getNewsBySource(source)
                    viewModel.getNewsBySource(source)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
//                    getNewsBySource(source)
                    viewModel.getNewsBySource(source)
                }
            }
        )
        viewDataBinding.tabLayout.getTabAt(0)?.select()
    }


}