package com.grishma.getarticles.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grishma.getarticles.model.Articles
import com.grishma.getarticles.utils.Converters

/**
 * Article Database initialization
 */
@Database(
    entities = [Articles.ArticlesItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticlesDb : RoomDatabase() {

  companion object {
    fun create(context: Context): ArticlesDb {
      val databaseBuilder = Room.databaseBuilder(context, ArticlesDb::class.java, "articles.db")
      return databaseBuilder.build()
    }
  }

  abstract fun articlesDao(): ArticlesDao
}
