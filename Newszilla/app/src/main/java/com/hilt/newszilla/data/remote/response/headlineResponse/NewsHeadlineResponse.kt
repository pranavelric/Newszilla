package com.hilt.newszilla.data.remote.response.headlineResponse

data class NewsHeadlineResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)