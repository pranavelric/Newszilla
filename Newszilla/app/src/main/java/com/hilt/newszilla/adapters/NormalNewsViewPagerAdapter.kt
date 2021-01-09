package com.hilt.newszilla.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NormalNewsViewPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(
    fm
) {

    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: MutableList<String> = ArrayList()


    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun getTitle(position: Int): String {
        return mFragmentTitleList[position]
    }


    override fun getItemCount(): Int {
        return mFragmentList.size
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

}