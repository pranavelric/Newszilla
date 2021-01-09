package com.hilt.newszilla.ui.onboarding

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.hilt.newszilla.BR
import com.hilt.newszilla.R
import com.hilt.newszilla.databinding.ActivityOnBoardingBinding
import com.hilt.newszilla.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_on_boarding.*
import javax.inject.Inject


@AndroidEntryPoint
class OnBoarding : BaseActivity<OnBoardingViewModel, ActivityOnBoardingBinding>() {

    private val mViewModel: OnBoardingViewModel by lazy {
        ViewModelProvider(this).get(OnBoardingViewModel::class.java)
    }


    @Inject
    lateinit var adapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = adapter
        worm_dots_indicator.setViewPager2(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mViewModel.position.value = position

                if (position == 2) {
                    btn_next_step.text = "Finish"
                }

            }
        }
        )


        mViewModel.position.observe(this, Observer {
            viewPager.setCurrentItem(it, true)
        })

    }


    override val layoutId: Int
        get() = R.layout.activity_on_boarding

    override fun getViewModel(): OnBoardingViewModel {
        return mViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.onBoardingBinding
    }
}