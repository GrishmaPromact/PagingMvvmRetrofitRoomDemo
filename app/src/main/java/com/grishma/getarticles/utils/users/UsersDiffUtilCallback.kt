package com.grishma.getarticles.utils.users

import androidx.recyclerview.widget.DiffUtil
import com.grishma.getarticles.model.UsersModel

/**
 * Users Util Callback
 */
class UsersDiffUtilCallback : DiffUtil.ItemCallback<UsersModel.UsersModelItem>() {

    override fun areItemsTheSame(
        oldItem: UsersModel.UsersModelItem,
        newItem: UsersModel.UsersModelItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UsersModel.UsersModelItem,
        newItem: UsersModel.UsersModelItem
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
