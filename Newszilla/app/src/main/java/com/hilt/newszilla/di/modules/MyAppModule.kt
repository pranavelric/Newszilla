package com.hilt.newszilla.di.modules

import android.content.Context
import com.hilt.newszilla.adapters.HeadlineNewsAdapter
import com.hilt.newszilla.adapters.NewsAdapter
import com.hilt.newszilla.adapters.SavedNewsAdapter
import com.hilt.newszilla.utils.BtmSheetDialog
import com.hilt.newszilla.utils.MySharedPrefrences
import com.hilt.newszilla.utils.SearchBtmSheetDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(FragmentComponent::class)
@Module
class MyAppModule {

    @FragmentScoped
    @Provides
    fun providesNewsAdapter(@ActivityContext context: Context): NewsAdapter {
        return NewsAdapter(context)
    }


    @FragmentScoped
    @Provides
    fun providesSavedNewsAdapter(@ActivityContext context: Context): SavedNewsAdapter {
        return SavedNewsAdapter(context)
    }


    @FragmentScoped
    @Provides
    fun providesHeadlineNewsAdapter(): HeadlineNewsAdapter {
        return HeadlineNewsAdapter()
    }

    @FragmentScoped
    @Provides
    fun providesBottomSheet(sharedPrefrences: MySharedPrefrences): BtmSheetDialog {
        return BtmSheetDialog(sharedPrefrences)
    }


    @FragmentScoped
    @Provides
    fun providesSearchBottomSheet(sharedPrefrences: MySharedPrefrences): SearchBtmSheetDialog {
        return SearchBtmSheetDialog(sharedPrefrences)
    }




}