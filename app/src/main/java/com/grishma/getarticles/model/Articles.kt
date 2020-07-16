package com.grishma.getarticles.model


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class for Articles
 */
class Articles : ArrayList<Articles.ArticlesItem>(){
    @Entity(tableName = "articles")
    data class ArticlesItem(
        @PrimaryKey()
        val id: String,
        val comments: Int,
        val content: String,
        val createdAt: String,
        val likes: Int,
        val media: List<Media>,
        val user: List<User>
    )

    {
        data class Media(
            val blogId: String,
            val createdAt: String,
            val id: String,
            val image: String,
            val title: String,
            val url: String
        )

        data class User(
            val id: String,
            val about: String,
            val avatar: String,
            val blogId: String,
            val city: String,
            val createdAt: String,
            val designation: String,
            val lastname: String,
            val name: String
        )
    }
}