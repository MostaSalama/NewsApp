package com.example.newsapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.*
import com.example.newsapp.api.ApiManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel:ViewModel() {

    val sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    val newsLiveData = MutableLiveData<List<ArticlesItem?>?>()
    val progressVisible = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()
     fun getNewsSources(category: Category) {
         progressVisible.value = true
        ApiManager.getApis()
            .getSources(Constant.apiKey,category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>,
                ) {
                        progressVisible.value = false
//                    progressBar.isVisible =false
//                    addSourcesToTabLayout(response.body()?.sources)
                    sourcesLiveData.value = response.body()?.sources
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    messageLiveData.value = t.localizedMessage
                    progressVisible.value = true
                }
            })
    }

     fun getNewsBySource(source: SourcesItem) {
         progressVisible.value = true
        ApiManager.getApis().getNews(Constant.apiKey,source.id?:"")
            .enqueue(object :Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>,
                ) {
                    newsLiveData.value = response.body()?.articles
                    progressVisible.value = false

         //           progressBar.isVisible = false
           //         adapter.changeData(response.body()?.articles)
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressVisible.value = false
                    messageLiveData.value = t.localizedMessage



                }
            })
    }
}