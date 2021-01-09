package com.hilt.newszilla.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hilt.newszilla.data.remote.response.headlineResponse.Article
import com.hilt.newszilla.data.remote.response.headlineResponse.NewsHeadlineResponse
import com.hilt.newszilla.data.repository.NewsRepository
import com.hilt.newszilla.ui.base.BaseViewModel
import com.hilt.newszilla.utils.NetworkHelper
import com.hilt.newszilla.utils.Resources
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class HomeViewModel
@ViewModelInject constructor(val newsRepository: NewsRepository, val networkHelper: NetworkHelper) :
    BaseViewModel() {


    val breakingNewsHeadline: MutableLiveData<Resources<NewsHeadlineResponse>> = MutableLiveData()
    var breakingNewsHeadlinePage = 1
    var breakingNewsResponse: NewsHeadlineResponse? = null

    var normalNewsHashMap: HashMap<String, MutableLiveData<Resources<NewsHeadlineResponse>>> =
        HashMap()
    var noramlNewsPage = 1


    var searchNews: MutableLiveData<Resources<NewsHeadlineResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsHeadlineResponse? = null


    init {
        normalNewsHashMap.put("general", MutableLiveData<Resources<NewsHeadlineResponse>>())
        normalNewsHashMap.put("science", MutableLiveData<Resources<NewsHeadlineResponse>>())
        normalNewsHashMap.put("sports", MutableLiveData<Resources<NewsHeadlineResponse>>())
        normalNewsHashMap.put("technology", MutableLiveData<Resources<NewsHeadlineResponse>>())
        normalNewsHashMap.put("business", MutableLiveData<Resources<NewsHeadlineResponse>>())
        normalNewsHashMap.put("health", MutableLiveData<Resources<NewsHeadlineResponse>>())
        normalNewsHashMap.put("entertainment", MutableLiveData<Resources<NewsHeadlineResponse>>())

        getBreakingNewsHeadline()
        getSearchNews("android")


    }


    fun getMyNetworkHelper(): NetworkHelper {
        return networkHelper
    }

    fun getBreakingNewsHeadline() =
        viewModelScope.launch {
//            breakingNewsHeadline.postValue(Resources.Loading())
//            val response = newsRepository.getHeadlines(breakingNewsHeadlinePage)
//            breakingNewsHeadline.postValue(handleBreakingNewsResponse(response))

            safeNewsHeadlineCall()
        }


    fun getSearchNews(query: String) =
        viewModelScope.launch {
//            searchNews.postValue(Resources.Loading())
//            val response = newsRepository.searchNews(query, searchNewsPage)
//            searchNews.postValue(handleSearchNewsResponse(response))
            safeSearchNewsCall(query)
        }


    fun getNoramalNews(category: String) =
        viewModelScope.launch {

            if (!normalNewsHashMap.containsKey(category)) {
                normalNewsHashMap.put(category, MutableLiveData<Resources<NewsHeadlineResponse>>())
            }


//            normalNewsHashMap[category]!!.postValue(Resources.Loading())
//
//            val response = newsRepository.getNews(category, noramlNewsPage)
//
//            normalNewsHashMap[category]?.postValue(handleNormalNewsResponse(response))

            safeNormalNewsCall(category)

        }


    private suspend fun safeNewsHeadlineCall() {
        breakingNewsHeadline.postValue(Resources.Loading())

        try {

            if (networkHelper.isNetworkConnected()) {

                val response = newsRepository.getHeadlines(breakingNewsHeadlinePage)
                breakingNewsHeadline.postValue(handleBreakingNewsResponse(response))
            } else {

                breakingNewsHeadline.postValue(Resources.Error("No internet connection"))
            }

        } catch (e: Exception) {
            when (e) {
                is IOException -> breakingNewsHeadline.postValue(Resources.Error("Network failure"))
                else -> breakingNewsHeadline.postValue(Resources.Error("Conversion errror"))
            }

        }
    }

    private suspend fun safeSearchNewsCall(query: String) {
        searchNews.postValue(Resources.Loading())
        try {
            if (networkHelper.isNetworkConnected()) {

                val response = newsRepository.searchNews(query, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resources.Error("No internet connection"))
            }

        } catch (e: Exception) {
            when (e) {
                is IOException -> searchNews.postValue(Resources.Error("Network failure"))
                else -> searchNews.postValue(Resources.Error("Conversion errror"))
            }

        }
    }

    private suspend fun safeNormalNewsCall(category: String) {
        normalNewsHashMap[category]!!.postValue(Resources.Loading())
        try {
            if (networkHelper.isNetworkConnected()) {
                val response = newsRepository.getNews(category, noramlNewsPage)

                normalNewsHashMap[category]?.postValue(handleNormalNewsResponse(response))
            } else {
                normalNewsHashMap[category]?.postValue(Resources.Error("No internet connection"))
            }

        } catch (e: Exception) {
            when (e) {
                is IOException -> normalNewsHashMap[category]?.postValue(Resources.Error("Network failure"))
                else -> normalNewsHashMap[category]?.postValue(Resources.Error("Conversion errror"))
            }

        }
    }


    private fun handleBreakingNewsResponse(response: Response<NewsHeadlineResponse>): Resources<NewsHeadlineResponse> {

        if (response.isSuccessful) {
            response.body()?.let {
                breakingNewsHeadlinePage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = it
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)


                }
                return Resources.Success(breakingNewsResponse ?: it)
            }
        }
        return Resources.Error(response.message())

    }


    private fun handleNormalNewsResponse(response: Response<NewsHeadlineResponse>): Resources<NewsHeadlineResponse> {

        if (response.isSuccessful) {
            response.body()?.let {


                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())

    }


    private fun handleSearchNewsResponse(response: Response<NewsHeadlineResponse>): Resources<NewsHeadlineResponse> {

        if (response.isSuccessful) {
            response.body()?.let {

                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = it
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resources.Success(searchNewsResponse ?: it)

                //    return Resources.Success( it)
            }
        }
        return Resources.Error(response.message())

    }


    fun saveArticle(article: Article) = viewModelScope.launch {

        newsRepository.upsert(article)
    }

    fun getSavedArticles() = newsRepository.getSavedArticles()


    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArtcile(article)
    }

}