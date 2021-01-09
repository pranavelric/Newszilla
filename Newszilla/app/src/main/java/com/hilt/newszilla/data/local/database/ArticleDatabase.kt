package com.hilt.newszilla.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hilt.newszilla.data.local.mappers.MyConverters
import com.hilt.newszilla.data.remote.response.headlineResponse.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(MyConverters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun mArticleDao(): ArticleDao

    companion object {

        val DATABASE_NAME: String = "ARTICLE_DB"


    }

}
