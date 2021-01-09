package com.hilt.newszilla.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilt.newszilla.R
import com.hilt.newszilla.data.remote.response.headlineResponse.Article
import com.hilt.newszilla.ui.home.HomeViewModel
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.item_every_news.view.*
import javax.inject.Inject



class SavedNewsAdapter
@Inject
constructor(@ActivityContext val mContext: Context) :
    RecyclerView.Adapter<SavedNewsAdapter.NewsViewHolder>() {


    lateinit var mViewModel: HomeViewModel

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {

            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_every_news, parent, false)


        return NewsViewHolder(v)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {


        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).centerCrop().into(image_news)
            text_name_publisher.text = article.author
            text_title.text = article.title
            text_description.text = article.description

            //have to set this
            text_date.text = article.publishedAt?.split("T")?.get(0)

            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }

            btnBookmark.isLiked = true

            btnBookmark.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {

                    //          mViewModel.saveArticle(article)


                }

                override fun unLiked(likeButton: LikeButton?) {

                    mViewModel.deleteArticle(article)


                }

            })


            btnShare.setOnClickListener {

                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody = article.url
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, article.title)
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"))

            }


        }


    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    fun setViewModel(model: HomeViewModel) {
        mViewModel = model
    }



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}