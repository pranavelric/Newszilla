package com.hilt.newszilla.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter
 constructor(
    var list: ArrayList<Fragment>,
    fm: FragmentActivity
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {

        return list[position]
    }
}