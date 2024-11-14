package com.kodiiiofc.urbanuniversity.daggerhilt.presetation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kodiiiofc.urbanuniversity.daggerhilt.R
import com.kodiiiofc.urbanuniversity.daggerhilt.domain.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleIV: ImageView = itemView.findViewById(R.id.image_article_iv)
        val articleDateIV: TextView = itemView.findViewById(R.id.date_article_tv)
        val articleTitleIV: TextView = itemView.findViewById(R.id.title_article_tv)
    }

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_article, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.articleIV)
            holder.articleDateIV.text = article.publishedAt
            holder.articleTitleIV.text = article.title
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}