package com.grishma.getarticles.utils.articles

import android.util.Log
import androidx.paging.PagedList
import com.grishma.getarticles.db.ArticlesDb
import com.grishma.getarticles.model.Articles
import com.grishma.getarticles.networking.ApiService
import com.grishma.getarticles.utils.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors

/**
 * ArticlesBoundary callback
 */
class ArticlesBoundaryCallback(private val db: ArticlesDb) :
    PagedList.BoundaryCallback<Articles.ArticlesItem>() {

    private val api = ApiService.createService()
    private val executor = Executors.newSingleThreadExecutor()
    private val helper =
        PagingRequestHelper(executor)
    private var page: Int = 1;

    //when 0 items loaded
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
            api.getArticles(1, 10)
                .enqueue(object : Callback<ArrayList<Articles.ArticlesItem>> {
                    override fun onFailure(
                        call: Call<ArrayList<Articles.ArticlesItem>>?,
                        t: Throwable
                    ) {
                        Log.e("TAG::", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<ArrayList<Articles.ArticlesItem>>?,
                        response: Response<ArrayList<Articles.ArticlesItem>>
                    ) {
                        val posts = response.body()?.toMutableList()
                        executor.execute {
                            db.articlesDao().insert(posts = posts)
                            helperCallback.recordSuccess()
                        }
                    }
                })
        }
    }

    //when items at end loaded
    override fun onItemAtEndLoaded(itemAtEnd: Articles.ArticlesItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
            api.getArticles(page++, limit = 10)
                .enqueue(object : Callback<ArrayList<Articles.ArticlesItem>> {

                    override fun onFailure(
                        call: Call<ArrayList<Articles.ArticlesItem>>?,
                        t: Throwable
                    ) {
                        Log.e("TAG::", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<ArrayList<Articles.ArticlesItem>>?,
                        response: Response<ArrayList<Articles.ArticlesItem>>
                    ) {

                        val posts = response.body()?.toMutableList()
                        executor.execute {
                            db.articlesDao().insert(posts = posts)
                            helperCallback.recordSuccess()
                        }
                    }
                })
        }
    }
}
