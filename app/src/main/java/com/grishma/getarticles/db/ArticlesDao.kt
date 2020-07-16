package com.grishma.getarticles.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grishma.getarticles.model.Articles

/**
 * ArticleDao class for Articles
 */
@Dao
interface ArticlesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(posts: MutableList<Articles.ArticlesItem>?)

  @Query("SELECT * FROM articles")
  fun getArticles(): DataSource.Factory<Int, Articles.ArticlesItem>

}
