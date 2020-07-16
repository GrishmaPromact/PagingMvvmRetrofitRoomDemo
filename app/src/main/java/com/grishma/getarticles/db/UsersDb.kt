package com.grishma.getarticles.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grishma.getarticles.model.UsersModel
import com.grishma.getarticles.utils.Converters

/**
 * Users Database initialization
 */
@Database(
    entities = [UsersModel.UsersModelItem::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UsersDb : RoomDatabase() {

  companion object {
    fun create(context: Context): UsersDb {
      val databaseBuilder = Room.databaseBuilder(context, UsersDb::class.java, "users.db")
          .fallbackToDestructiveMigration()
      return databaseBuilder.build()
    }
  }

  abstract fun usersDao(): UsersDao
}
