package com.hilt.newszilla.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MySharedPrefrences @Inject constructor(@ApplicationContext context: Context) {

    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(AppConstants.SHARED_PREF, 0)
    }

    private var editor = sp.edit()


    fun cleaSession() {
        editor.clear()
        editor.commit()
    }

    fun setIsOnBoardingCompleted() {
        editor.putBoolean(AppConstants.IS_ONBOARDING_COMPLETED, true)
        editor.commit()
    }

    fun getIsOnBoardingCompleted(): Boolean {
        return sp.getBoolean(AppConstants.IS_ONBOARDING_COMPLETED, false)
    }


    fun isNightModeEnabled(): Boolean {
        return sp.getBoolean(AppConstants.NIGHT_MODE, false)
    }

    fun setIsNightModeEnabled(state: Boolean) {
        editor.putBoolean(AppConstants.NIGHT_MODE, state)
        editor.commit()
    }

    fun selectedNewsSource(source: String) {
        editor.putString(AppConstants.NEWS_SOURCE, source)
        editor.commit()

    }

    fun selectedNewsCountry(country: String) {
        editor.putString(AppConstants.NEWS_COUNTRY, country)
        editor.commit()

    }

    fun getNewsSource(): String {
        return sp.getString(AppConstants.NEWS_SOURCE, "the-hindu") ?: "the-hindu"
    }

    fun getCountry(): String {
        return sp.getString(AppConstants.NEWS_COUNTRY, "in") ?: "in"
    }



    fun selectedLanguage(lan: String) {
        editor.putString(AppConstants.NEWS_LANGUAGE, lan)
        editor.commit()

    }

    fun selectedNewsSortBy(sortby: String) {
        editor.putString(AppConstants.NEWS_SORTBY, sortby)
        editor.commit()

    }

    fun getNewsLanguage(): String {
        return sp.getString(AppConstants.NEWS_LANGUAGE, "en") ?: "en"
    }

    fun getSortBy(): String {
        return sp.getString(AppConstants.NEWS_SORTBY, "publishedAt") ?: "publishedAt"
    }




}