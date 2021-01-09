package com.hilt.newszilla.di.modules

import android.content.Context
import androidx.room.Room
import com.hilt.newszilla.data.local.database.ArticleDao
import com.hilt.newszilla.data.local.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomDatabaseModule {

    @Singleton
    @Provides
    fun providesArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    }

    @Singleton
    @Provides
    fun providesArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.mArticleDao()
    }


}