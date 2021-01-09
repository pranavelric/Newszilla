package com.hilt.newszilla.ui.home.newsFragments.breakingNewsFragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.hilt.newszilla.R

import com.hilt.newszilla.adapters.HeadlineNewsAdapter
import com.hilt.newszilla.adapters.NormalNewsViewPagerAdapter
import com.hilt.newszilla.ui.base.BaseHomeFragment
import com.hilt.newszilla.ui.home.newsFragments.genericNormalNewsFragment.NormalNewsFragment
import com.hilt.newszilla.utils.AppConstants
import com.hilt.newszilla.utils.BtmSheetDialog
import com.hilt.newszilla.utils.Resources
import com.hilt.newszilla.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.breaking_news_fragment.*

import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment : BaseHomeFragment(R.layout.breaking_news_fragment) {

    @Inject
    lateinit var headlineNewsAdapter: HeadlineNewsAdapter

    @Inject
    lateinit var btmSheetDialog: BtmSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setAdapter()

        if (!homeViewModel.getMyNetworkHelper().isNetworkConnected()) {
           activity?.showAlertDialog()

        }



        headlineNewsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleNewsFragment,
                bundle
            )

        }


        val mViewPagerAdapter = NormalNewsViewPagerAdapter(
            requireActivity()
        )
        mViewPagerAdapter.addFragment(NormalNewsFragment.newInstance("general"), "General")
        mViewPagerAdapter.addFragment(NormalNewsFragment.newInstance("science"), "Science")
        mViewPagerAdapter.addFragment(NormalNewsFragment.newInstance("sports"), "Sports")
        mViewPagerAdapter.addFragment(NormalNewsFragment.newInstance("technology"), "Technology")
        mViewPagerAdapter.addFragment(NormalNewsFragment.newInstance("business"), "Business")
        mViewPagerAdapter.addFragment(NormalNewsFragment.newInstance("health"), "Health")
        mViewPagerAdapter.addFragment(
            NormalNewsFragment.newInstance("entertainment"),
            "Entertainment"
        )
        view_pager_normal_news.adapter = mViewPagerAdapter
        view_pager_normal_news.offscreenPageLimit = 4
        TabLayoutMediator(tabs, view_pager_normal_news) { tab, position ->
            tab.text = mViewPagerAdapter.getTitle(position)
        }.attach()





        homeViewModel.breakingNewsHeadline.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resources.Success -> {

                    // hideProgressBar()
                    isLoading = false

                    it.data?.let { newsResponse ->
                        headlineNewsAdapter.differ.submitList(newsResponse.articles.toList())


                        val totalPages =
                            newsResponse.totalResults / AppConstants.QUERY_HEADLINE_PAGE_SIZE + 2
                        isLastPage = homeViewModel.breakingNewsHeadlinePage == totalPages

                    }
                }
                is Resources.Error -> {
                    //hideProgressBar()
                    isLoading = false

                    it.message?.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_LONG)
                    }
                }
                is Resources.Loading -> {
                    //                  showProgressBar()
                    isLoading = true

                }
            }

        })

        menu.setOnClickListener {
            btmSheetDialog.show(activity?.supportFragmentManager!!, "Settings bottom sheet")
        }


    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val myScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)


            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val fisrtVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount


            val isNotLoadingAndNotAtLastPage = !isLoading && !isLastPage
            val isAtLastItem = fisrtVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = fisrtVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= AppConstants.QUERY_HEADLINE_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotAtLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                homeViewModel.getBreakingNewsHeadline()
                isScrolling = false
            }


        }


    }


    private fun setAdapter() {

        headlines_recycler_view.apply {
            adapter = headlineNewsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            addOnScrollListener(myScrollListener)
        }


    }


//    private fun showBottomSheetSearch() {
//        val view: View =
//            layoutInflater.inflate(R.layout.bottom_sheet_background, null)
//
//        val btnValidateSignature = view.btn_apply
//        val spinnerLang: Spinner = view.spinner_lang
//        ArrayAdapter.createFromResource(
//            context!!,
//            R.array.languages,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinnerLang.adapter = adapter
//            val spinnerPosition = adapter.getPosition(l.toUpperCase())
//            spinnerLang.setSelection(spinnerPosition)
//        }
//
//        val languages = resources.getStringArray(R.array.languages_values)
//        spinnerLang.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View, position: Int, id: Long
//            ) {
//                l = languages[position]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//        }
//
//        val spinnerSort: Spinner = view.spinner_sortby
//        ArrayAdapter.createFromResource(
//            context!!,
//            R.array.options_values,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinnerSort.adapter = adapter
//            val spinnerPosition = adapter.getPosition(s)
//            spinnerSort.setSelection(spinnerPosition)
//        }
//
//        val sorts = resources.getStringArray(R.array.options_values)
//        spinnerSort.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View, position: Int, id: Long
//            ) {
//                s = sorts[position]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//        }
//
//        val dialog = BottomSheetDialog(context!!)
//        dialog.setContentView(view)
//        val displayMetrics = this.resources.displayMetrics
//        val width = displayMetrics.widthPixels
//        val height = displayMetrics.heightPixels
//        val maxHeight = (height * 0.88).toInt()
//        val mBehavior: BottomSheetBehavior<*> =
//            BottomSheetBehavior.from(view.parent as View)
//        mBehavior.peekHeight = maxHeight
//        dialog.show()
//        textClose.setOnClickListener { dialog.dismiss() }
//        btnValidateSignature.setOnClickListener {
//            dialog.dismiss()
//            fetchApiNews(q, l, s)
//        }
//    }


}