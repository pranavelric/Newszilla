package com.hilt.newszilla.di.modules

import com.hilt.newszilla.data.remote.api.NewsApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {




    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(
            "https://newsapi.org/"
        ).addConverterFactory(MoshiConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit.Builder): NewsApi {
        return retrofit.build().create(NewsApi::class.java)
    }

}