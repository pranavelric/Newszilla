package com.hilt.newszilla.ui.home.newsFragments.searchNewsFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hilt.newszilla.R
import com.hilt.newszilla.adapters.NewsAdapter
import com.hilt.newszilla.ui.base.BaseHomeFragment
import com.hilt.newszilla.utils.AppConstants
import com.hilt.newszilla.utils.Resources
import com.hilt.newszilla.utils.SearchBtmSheetDialog
import com.hilt.newszilla.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_news_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class SearchNewsFragment : BaseHomeFragment(R.layout.search_news_fragment) {

    @Inject
    lateinit var newsAdapter: NewsAdapter

    var searchText: String = "android"

    @Inject
    lateinit var searchBtmSheetDialog: SearchBtmSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        newsAdapter.setViewModel(homeViewModel)
        setAdapter()


        if (!homeViewModel.getMyNetworkHelper().isNetworkConnected()) {
            activity?.showAlertDialog()

        }
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleNewsFragment,
                bundle
            )

        }


        var job: Job? = null
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.searchNewsPage = 1
                homeViewModel.searchNewsResponse = null

                job?.cancel()
                job = MainScope().launch {
                    query?.let {
                        searchText = query
                        homeViewModel.getSearchNews(query)
                    }

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.searchNewsPage = 1
                homeViewModel.searchNewsResponse = null

                job?.cancel()
                job = MainScope().launch {
                    delay(AppConstants.SEARCH_NEWS_DELAY)

                    newText?.let {
                        searchText = newText
                        homeViewModel.getSearchNews(newText)
                        joinAll()


                    }

                }
                return false
            }

        })


        homeViewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resources.Success -> {

                    hideProgressBar()
                    it.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())

                        val totalPages =
                            newsResponse.totalResults / AppConstants.QUERY_PAGE_SIZE + 2
                        isLastPage = homeViewModel.searchNewsPage == totalPages

                        if (isLastPage) {
                            search_recycler_view.setPadding(0, 0, 0, 0)
                        }


                    }
                }
                is Resources.Error -> {

                    hideProgressBar()
                    it.message?.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_LONG)
                    }
                }
                is Resources.Loading -> {
                    showProgressBar()
                }
            }

        })






        search_settings_btn.setOnClickListener {

            searchBtmSheetDialog.show(activity?.supportFragmentManager!!, "Settings bottom sheet")
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
            val isTotalMoreThanVisible = totalItemCount >= AppConstants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotAtLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                homeViewModel.getSearchNews(searchText)
                isScrolling = false
            }


        }


    }


    fun setAdapter() {

        search_recycler_view.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

            addOnScrollListener(myScrollListener)

        }

    }

    fun hideProgressBar() {
        // progress_layout.visibility = View.GONE
        paginationProgressBar.visibility = View.GONE

        isLoading = false

    }


    fun showProgressBar() {
        //   progress_layout.visibility = View.VISIBLE

        paginationProgressBar.visibility = View.VISIBLE

        isLoading = true

    }

}