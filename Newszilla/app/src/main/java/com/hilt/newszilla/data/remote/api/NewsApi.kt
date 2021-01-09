package com.hilt.newszilla.data.remote.api

import com.hilt.newszilla.data.remote.response.headlineResponse.NewsHeadlineResponse
import com.hilt.newszilla.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("sources")
        sources: String = "bbc-news",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = AppConstants.API_KEY

    ): Response<NewsHeadlineResponse>


    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String = "in",
        @Query("category")
        newsCategory: String = "technology",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = AppConstants.API_KEY

    ): Response<NewsHeadlineResponse>


    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("language")
        language: String = "en",
        @Query("sortBy")
        sortBy: String = "publishedAt",
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int = AppConstants.SEARCH_PAGE_SIZE,
        @Query("apiKey")
        apiKey: String = AppConstants.API_KEY

    ): Response<NewsHeadlineResponse>


}