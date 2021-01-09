package com.hilt.newszilla.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hilt.newszilla.data.remote.response.headlineResponse.Article


@Dao
interface ArticleDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM article")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)


}