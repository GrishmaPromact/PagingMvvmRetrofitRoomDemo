package com.grishma.getarticles.networking

import com.grishma.getarticles.model.Articles
import com.grishma.getarticles.model.UsersModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Api Service class
 */
interface ApiService {

    @GET("/blogs")
    fun getArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<ArrayList<Articles.ArticlesItem>>

    @GET("/users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<ArrayList<UsersModel.UsersModelItem>>

  companion object {
    private const val BASE_URL = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/"

    fun createService(): ApiService {
      return Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(ApiService::class.java)
    }
  }
}
