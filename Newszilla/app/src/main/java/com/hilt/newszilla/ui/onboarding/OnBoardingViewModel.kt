package com.hilt.newszilla.ui.onboarding

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.hilt.newszilla.ui.base.BaseViewModel
import com.hilt.newszilla.ui.home.HomeActivity
import com.hilt.newszilla.utils.MySharedPrefrences
import com.hilt.newszilla.utils.transitionAnimationBundle

class OnBoardingViewModel @ViewModelInject constructor(
    var sharedPrefrences: MySharedPrefrences
) :
    BaseViewModel() {


    var btnNext = ObservableField<String>("next")
    var position: MutableLiveData<Int> = MutableLiveData(0)


    fun onClickNext(view: View) {

        if (position.value!! >= 2) {
            btnNext.set("Finish")
            sharedPrefrences.setIsOnBoardingCompleted()

            Intent(view.context, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                view.context.startActivity(it, view.context.transitionAnimationBundle())
            }

        } else {
            position.value = position.value?.plus(1)
            if (position.value!! == 2) {
                btnNext.set("Finish")
            }
        }
    }

    fun onClickBack(view: View) {
        (view.context as Activity).finishAffinity()
    }

}