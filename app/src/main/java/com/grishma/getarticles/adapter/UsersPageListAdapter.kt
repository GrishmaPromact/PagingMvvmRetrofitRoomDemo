package com.grishma.getarticles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grishma.getarticles.R
import com.grishma.getarticles.model.UsersModel
import com.grishma.getarticles.utils.users.UsersDiffUtilCallback
import kotlinx.android.synthetic.main.item_article.view.ivUserImage
import kotlinx.android.synthetic.main.item_article.view.tvUserDesignation
import kotlinx.android.synthetic.main.item_article.view.tvUserName
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * User Page List Adapter
 */
class UsersPageListAdapter : PagedListAdapter<UsersModel.UsersModelItem, UsersPageListAdapter.UsersViewHolder>(
    UsersDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val articlesItem = getItem(position)
        val resources = holder.itemView.context.resources

        Glide.with(holder.itemView.context).load(articlesItem!!.avatar)
            .apply(RequestOptions().circleCrop())
            .placeholder(R.drawable.user_placeholder)
            .into(holder.itemView.ivUserImage)

        holder.itemView.tvUserName.text = articlesItem.name
        holder.itemView.tvUserDesignation.text = articlesItem.designation
        holder.itemView.tvUserCity.text = articlesItem.city

    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}