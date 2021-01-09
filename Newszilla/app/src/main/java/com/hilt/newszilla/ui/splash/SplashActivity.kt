package com.hilt.newszilla.ui.splash

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hilt.newszilla.BR
import com.hilt.newszilla.R
import com.hilt.newszilla.databinding.ActivitySplashBinding
import com.hilt.newszilla.ui.base.BaseActivity
import com.hilt.newszilla.utils.AppConstants
import com.hilt.newszilla.utils.FirebaseUtil
import com.hilt.newszilla.utils.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    @Inject
    lateinit var firebaseUtil: FirebaseUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



           firebaseUtil.getKeyFromDatabase()

                mViewModel.checkFirstTimeLaunch().observe(this@SplashActivity, Observer {


                    if (it) {
                        goToOnBoarding()
                    } else {
                        goToHomeActivity()
                    }
                })



    }

    private fun goToHomeActivity() {
        mViewModel.goToHomeActivity(this)
    }

    private fun goToOnBoarding() {
        mViewModel.goToOnBoardingActivity(this)
    }


    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun getViewModel(): SplashViewModel {
        return mViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.splashBinding
    }


}