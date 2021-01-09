package com.hilt.newszilla.data.repository

import com.hilt.newszilla.data.local.database.ArticleDatabase
import com.hilt.newszilla.data.remote.api.NewsApi
import com.hilt.newszilla.data.remote.response.headlineResponse.Article
import com.hilt.newszilla.utils.MySharedPrefrences
import javax.inject.Inject


class NewsRepository
@Inject constructor(
    val db: ArticleDatabase,
    val retrofitService: NewsApi,
    val mySharedPrefrences: MySharedPrefrences
) {


    suspend fun getHeadlines( pageNummer: Int) =
        retrofitService.getHeadlines(sources = mySharedPrefrences.getNewsSource(), pageNummer)


    suspend fun getNews(category: String, pageNummer: Int) =
        retrofitService.getNews(countryCode = mySharedPrefrences.getCountry(), category, pageNummer)

    suspend fun searchNews(query: String, pageNummer: Int) =
        retrofitService.searchNews(
            searchQuery = query,
            pageNumber = pageNummer,
            language = mySharedPrefrences.getNewsLanguage(),
            sortBy = mySharedPrefrences.getSortBy()
        )

    suspend fun upsert(article: Article) = db.mArticleDao().upsert(article)

    fun getSavedArticles() = db.mArticleDao().getAllArticles()

    suspend fun deleteArtcile(article: Article) = db.mArticleDao().deleteArticle(article)


}