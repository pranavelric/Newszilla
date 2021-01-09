package com.hilt.newszilla.data.remote.response.headlineResponse

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "article", indices = [Index(value = ["url"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable