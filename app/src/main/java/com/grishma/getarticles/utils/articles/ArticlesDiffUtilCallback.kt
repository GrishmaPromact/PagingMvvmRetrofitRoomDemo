package com.grishma.getarticles.utils.articles

import androidx.recyclerview.widget.DiffUtil
import com.grishma.getarticles.model.Articles

/**
 * Articles Callback
 */
class ArticlesDiffUtilCallback : DiffUtil.ItemCallback<Articles.ArticlesItem>() {

    override fun areItemsTheSame(
        oldItem: Articles.ArticlesItem,
        newItem: Articles.ArticlesItem
    ): Boolean {
        return oldItem.id == newItem?.id
    }

    override fun areContentsTheSame(
        oldItem: Articles.ArticlesItem,
        newItem: Articles.ArticlesItem
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
