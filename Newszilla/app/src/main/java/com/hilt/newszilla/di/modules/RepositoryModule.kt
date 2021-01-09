package com.hilt.newszilla.di.modules

import android.content.Context
import com.hilt.newszilla.data.local.database.ArticleDatabase
import com.hilt.newszilla.data.remote.api.NewsApi
import com.hilt.newszilla.data.repository.NewsRepository
import com.hilt.newszilla.utils.MySharedPrefrences
import com.hilt.newszilla.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        db: ArticleDatabase,
        service: NewsApi,
        mySharedPrefrences: MySharedPrefrences
    ): NewsRepository {
        return NewsRepository(db, service, mySharedPrefrences)
    }


    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context )
    }
}