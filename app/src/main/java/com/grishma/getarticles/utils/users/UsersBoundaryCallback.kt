package com.grishma.getarticles.utils.users

import android.util.Log
import androidx.paging.PagedList
import com.grishma.getarticles.db.UsersDb
import com.grishma.getarticles.model.UsersModel
import com.grishma.getarticles.networking.ApiService
import com.grishma.getarticles.utils.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors

/**
 * Users Boundary Callback
 */
class UsersBoundaryCallback(private val db: UsersDb) :
    PagedList.BoundaryCallback<UsersModel.UsersModelItem>() {

    private val api = ApiService.createService()
    private val executor = Executors.newSingleThreadExecutor()
    private val helper =
        PagingRequestHelper(executor)
    private var page: Int = 1;

    //when 0 items loaded
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->
            api.getUsers(1, 10)
                .enqueue(object : Callback<ArrayList<UsersModel.UsersModelItem>> {

                    override fun onFailure(
                        call: Call<ArrayList<UsersModel.UsersModelItem>>?,
                        t: Throwable
                    ) {
                        Log.e("TAG::", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<ArrayList<UsersModel.UsersModelItem>>?,
                        response: Response<ArrayList<UsersModel.UsersModelItem>>
                    ) {
                        val posts = response.body()?.toMutableList()
                        executor.execute {
                            db.usersDao().insertUser(posts = posts)
                            helperCallback.recordSuccess()
                        }
                    }
                })
        }
    }

    //when items at end loaded
    override fun onItemAtEndLoaded(itemAtEnd: UsersModel.UsersModelItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
            api.getUsers(page++, limit = 10)
                .enqueue(object : Callback<ArrayList<UsersModel.UsersModelItem>> {

                    override fun onFailure(
                        call: Call<ArrayList<UsersModel.UsersModelItem>>?,
                        t: Throwable
                    ) {
                        Log.e("TAG::", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<ArrayList<UsersModel.UsersModelItem>>?,
                        response: Response<ArrayList<UsersModel.UsersModelItem>>
                    ) {

                        val posts = response.body()?.toMutableList()
                        executor.execute {
                            db.usersDao().insertUser(posts = posts)
                            helperCallback.recordSuccess()
                        }
                    }
                })
        }
    }
}
