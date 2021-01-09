package com.hilt.newszilla.ui.home.newsFragments.articleNewsFragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hilt.newszilla.R
import com.hilt.newszilla.ui.base.BaseHomeFragment
import com.hilt.newszilla.utils.snackbar
import kotlinx.android.synthetic.main.article_news_fragment.*

class ArticleNewsFragment : BaseHomeFragment(R.layout.article_news_fragment) {


    val args: ArticleNewsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  homeViewModel
        fab?.isEnabled = false
        progress_layout?.visibility = View.VISIBLE
        val article = args.article
        webView.apply {

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progress_layout?.visibility = View.VISIBLE

                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progress_layout?.visibility = View.GONE
                    fab?.isEnabled = true
                    super.onPageFinished(view, url)
                }




            }
            article.url?.let { loadUrl(it) }
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true


        }


        fab?.setOnClickListener {
            homeViewModel.saveArticle(article)

            view.snackbar("Article saved")
        }


    }


}