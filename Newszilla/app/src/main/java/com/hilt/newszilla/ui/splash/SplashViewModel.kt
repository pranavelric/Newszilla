package com.hilt.newszilla.ui.splash

import android.content.Context
import android.content.Intent
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hilt.newszilla.ui.base.BaseViewModel
import com.hilt.newszilla.ui.home.HomeActivity
import com.hilt.newszilla.ui.onboarding.OnBoarding
import com.hilt.newszilla.utils.Coroutines
import com.hilt.newszilla.utils.FirebaseUtil
import com.hilt.newszilla.utils.MySharedPrefrences
import com.hilt.newszilla.utils.transitionAnimationBundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class SplashViewModel @ViewModelInject constructor(
    var sharedPrefrences: MySharedPrefrences,

    ) : BaseViewModel() {





    var fistTimeLaunch = MutableLiveData<Boolean>(true)


    fun checkFirstTimeLaunch(): LiveData<Boolean> {

        fistTimeLaunch.value = !sharedPrefrences.getIsOnBoardingCompleted()



        return fistTimeLaunch
    }

    fun goToHomeActivity(context: Context) {
        Coroutines.mainWithDelay {
            Intent(context, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(it, context.transitionAnimationBundle())
            }
        }
    }

    fun goToOnBoardingActivity(context: Context) {
        Coroutines.mainWithDelay {
            Intent(context, OnBoarding::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(it, context.transitionAnimationBundle())
            }
        }
    }
}