package com.hilt.newszilla.di.modules

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.hilt.newszilla.ui.onboarding.OnBoardingAdapter
import com.hilt.newszilla.ui.onboarding.screens.FirstScreen
import com.hilt.newszilla.ui.onboarding.screens.SecondScreen
import com.hilt.newszilla.ui.onboarding.screens.ThirdScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
class OnBoradingModule {


    @Provides
    fun providesFirstScreen(): FirstScreen {
        return FirstScreen()
    }

    @Provides
    fun providesSecondScreen(): SecondScreen {
        return SecondScreen()
    }

    @Provides
    fun providesThirdScreen(): ThirdScreen {
        return ThirdScreen()
    }

    @Provides
    fun fragmentManager(activity: Activity): FragmentManager? {
        return (activity as AppCompatActivity).supportFragmentManager
    }


    @Provides
    fun getOnBoardingAdapter(
        activity: Activity,
        firstScreen: FirstScreen,
        secondScreen: SecondScreen,
        thirdScreen: ThirdScreen
    ): OnBoardingAdapter {
        return OnBoardingAdapter(
            arrayListOf(firstScreen, secondScreen, thirdScreen),
            activity as FragmentActivity
        )
    }


}