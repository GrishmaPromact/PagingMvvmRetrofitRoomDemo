package com.grishma.getarticles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grishma.getarticles.R
import com.grishma.getarticles.model.Articles
import com.grishma.getarticles.utils.articles.ArticlesDiffUtilCallback
import kotlinx.android.synthetic.main.item_article.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Article Page List Adapter
 */
class ArticlesPageListAdapter(private val itemClickListener: ItemClickListener?) :
    PagedListAdapter<Articles.ArticlesItem, ArticlesPageListAdapter.ArticlesViewHolder>(
        ArticlesDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val articlesItem = getItem(position)
        val resources = holder.itemView.context.resources

        Glide.with(holder.itemView.context).load(articlesItem!!.user[0].avatar)
            .apply(RequestOptions().circleCrop())
            .placeholder(R.drawable.user_placeholder)
            .into(holder.itemView.ivUserImage)

        holder.itemView.tvUserName.text = articlesItem.user[0].name
        holder.itemView.tvUserDesignation.text = articlesItem.user[0].designation

        if (articlesItem.media.isNotEmpty()) {
            if (articlesItem.media[0].image.isNotEmpty()) {
                holder.itemView.ivArticleImage.visibility = View.VISIBLE
                Glide.with(holder.itemView.context).load(articlesItem.media[0].image)
                    .placeholder(R.drawable.user_placeholder)
                    .into(holder.itemView.ivArticleImage)
            } else {
                holder.itemView.ivArticleImage.visibility = View.GONE
            }

            if (articlesItem.media[0].title.isNotEmpty()) {
                holder.itemView.tvArticleTitle.visibility = View.VISIBLE
                holder.itemView.tvArticleTitle.text = articlesItem.media[0].title
            } else {
                holder.itemView.tvArticleTitle.visibility = View.GONE
            }

            if (articlesItem.media[0].url.isNotEmpty()) {
                holder.itemView.tvArticleUrl.visibility = View.VISIBLE
                holder.itemView.tvArticleUrl.text = articlesItem.media[0].url
            } else {
                holder.itemView.tvArticleUrl.visibility = View.GONE
            }
        } else {
            holder.itemView.ivArticleImage.visibility = View.GONE
            holder.itemView.tvArticleTitle.visibility = View.GONE
            holder.itemView.tvArticleUrl.visibility = View.GONE
        }
        if (articlesItem.content.isNotEmpty()) holder.itemView.tvArticleDesc.text =
            articlesItem!!.content

        if(articlesItem.createdAt.isNotEmpty())
            getTime(articlesItem.createdAt, holder.itemView.tvTime)

        holder.itemView.tvLikes.text = formatLikesComments(articlesItem.likes.toString()) + " Likes"
        holder.itemView.tvComments.text =
            formatLikesComments(articlesItem.comments.toString()) + " Comments"

        holder.itemView.llUserNameLayout.setOnClickListener {
            itemClickListener?.OnItemClick(articlesItem)
        }
    }

    /**
     * get remaining time from created date format
     */
    private fun getTime(createdAt: String, tvTime: AppCompatTextView) {

        val format =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        try {
            val date = format.parse(createdAt)
            val time = date.time
            val remainingTime = System.currentTimeMillis() - time

            val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime)
            val hour = TimeUnit.MILLISECONDS.toHours(remainingTime)
            val day = TimeUnit.MILLISECONDS.toDays(remainingTime)

            when {
                seconds < 60 -> {
                    tvTime.text = "$seconds sec"
                }
                minutes < 60 -> {
                    tvTime.text = "$minutes min"
                }
                hour < 24 -> {
                    tvTime.text = "$hour hr"
                }
                else -> {
                    tvTime.text = "$day days"
                }
            }

            println(date)
            println(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    /**
     * ArticlesViewHolder
     */
    class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * On item click listener
     */
    interface ItemClickListener {
        fun OnItemClick(articlesItem: Articles.ArticlesItem?)
    }

    /**
     * convert string value of likes and comments to K , L , C format
     */
    private fun formatLikesComments(likesComments: String): String? {
        var formatBalance = -1
        var symbol = ""
        try {
            val coinsInt = likesComments.toInt()
            if (coinsInt / 10000000 == 0) {
                if (coinsInt / 100000 == 0) {
                    if (coinsInt / 1000 == 0) {
                        symbol = ""
                    } else {
                        symbol = "K"
                        formatBalance = coinsInt / 1000
                        if (coinsInt % 1000 != 0) {
                            symbol = "K+"
                        }
                    }
                } else {
                    symbol = "L"
                    formatBalance = coinsInt / 100000
                    if (coinsInt % 100000 != 0) {
                        symbol = "L+"
                    }
                }
            } else {
                symbol = "C"
                formatBalance = coinsInt / 10000000
                if (coinsInt % 10000000 != 0) {
                    symbol = "K+"
                }
            }
            if (formatBalance == -1) {
                formatBalance = likesComments.toInt()
            }
            return formatBalance.toString() + symbol
        } catch (e: Exception) {
        }
        return ""
    }
}