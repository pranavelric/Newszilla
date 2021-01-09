package com.hilt.newszilla.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hilt.newszilla.ui.home.HomeActivity
import com.hilt.newszilla.ui.home.HomeViewModel

abstract class BaseHomeFragment(var layoutId: Int) : Fragment(layoutId) {


    protected lateinit var homeViewModel: HomeViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = (activity as HomeActivity).mViewModel

    }


}