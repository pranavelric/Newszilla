package com.hilt.newszilla.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hilt.newszilla.R
import com.hilt.newszilla.data.remote.response.headlineResponse.Article
import kotlinx.android.synthetic.main.item_headline_news.view.*


class HeadlineNewsAdapter : RecyclerView.Adapter<HeadlineNewsAdapter.HeadlineNewsViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {

            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineNewsViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_headline_news, parent, false)
        return HeadlineNewsViewHolder(v)
    }

    override fun onBindViewHolder(holder: HeadlineNewsViewHolder, position: Int) {

        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(url_image)
            title.text = article.title
            description.text = article.description
            author.text = article.author

            //have to set this
            time.text = article.publishedAt?.split("T")?.get(0)

            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
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


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class HeadlineNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}