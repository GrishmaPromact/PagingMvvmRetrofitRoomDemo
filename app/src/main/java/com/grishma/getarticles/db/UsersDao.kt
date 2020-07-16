package com.grishma.getarticles.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grishma.getarticles.model.UsersModel

/**
 * UserDao class for users
 */
@Dao
interface UsersDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertUser(posts: MutableList<UsersModel.UsersModelItem>?)

  @Query("SELECT * FROM users")
  fun getUsers(): DataSource.Factory<Int, UsersModel.UsersModelItem>
}
