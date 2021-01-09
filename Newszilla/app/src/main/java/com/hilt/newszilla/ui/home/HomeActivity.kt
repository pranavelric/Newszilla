package com.hilt.newszilla.ui.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hilt.newszilla.BR
import com.hilt.newszilla.R
import com.hilt.newszilla.databinding.ActivityHomeBinding
import com.hilt.newszilla.ui.base.BaseActivity
import com.hilt.newszilla.ui.base.BaseViewModel
import com.hilt.newszilla.utils.NetworkHelper
import com.hilt.newszilla.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<BaseViewModel, ActivityHomeBinding>() {

    val mViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)



        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }

    override val layoutId: Int
        get() = R.layout.activity_home

    override fun getViewModel(): BaseViewModel {
        return mViewModel

    }

    override fun getBindingVariable(): Int {
        return BR.homeBinding
    }
}