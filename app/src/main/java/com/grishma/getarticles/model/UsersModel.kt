package com.grishma.getarticles.model


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for users
 */
class UsersModel : ArrayList<UsersModel.UsersModelItem>(){
    @Entity(tableName = "users")
    data class UsersModelItem(
        @PrimaryKey()
        val id: String,
        val about: String,
        val avatar: String,
        val city: String,
        val createdAt: String,
        val designation: String,
        val lastname: String,
        val name: String
    )
}