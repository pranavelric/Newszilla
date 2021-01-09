package com.hilt.newszilla.ui.home.newsFragments.genericNormalNewsFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hilt.newszilla.R
import com.hilt.newszilla.adapters.NewsAdapter
import com.hilt.newszilla.ui.base.BaseHomeFragment
import com.hilt.newszilla.utils.AppConstants
import com.hilt.newszilla.utils.AppConstants.ARG_CATEGORY
import com.hilt.newszilla.utils.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_normal_news.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NormalNewsFragment : BaseHomeFragment(R.layout.fragment_normal_news) {


    @Inject
    lateinit var newsAdapter: NewsAdapter

    var category = "general"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        newsAdapter.setViewModel(homeViewModel)
        setAdapter()



        if (arguments != null) {
            category = arguments?.getString(ARG_CATEGORY).toString()
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleNewsFragment,
                bundle
            )

        }


        homeViewModel.getNoramalNews( category)
        refresh_layout.setOnRefreshListener {
            homeViewModel.getNoramalNews( category)
            normal_news_recycler_view.scheduleLayoutAnimation()
            refresh_layout.isRefreshing = false
        }




        homeViewModel.normalNewsHashMap[category]?.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resources.Success -> {

                    hideProgressBar()
                    it.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

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

    }

    fun hideProgressBar() {
        progress_layout.visibility = View.GONE


    }

    fun showProgressBar() {
        progress_layout.visibility = View.VISIBLE


    }


    private fun setAdapter() {

        normal_news_recycler_view.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }


    }

    companion object {

        @JvmStatic
        fun newInstance(category: String) =
            NormalNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }

}