package com.grishma.getarticles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grishma.getarticles.R
import com.grishma.getarticles.adapter.UsersPageListAdapter
import com.grishma.getarticles.db.UsersDb
import com.grishma.getarticles.model.UsersModel
import com.grishma.getarticles.utils.users.UsersBoundaryCallback

/**
 * Users List Fragment to display Articles
 */
class UsersListFragment : Fragment(){
    private lateinit var usersRecyclerView1: RecyclerView
    private val adapter = UsersPageListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_users, container, false)
        usersRecyclerView1 = view.findViewById(R.id.usersRecyclerView) as RecyclerView
        initializeList()
        return view
    }

    private fun initializeList() {
        usersRecyclerView1.layoutManager = LinearLayoutManager(this.context)
        usersRecyclerView1.adapter = adapter

        //set paging configs
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        //observe users list and submit it into adapter
        val liveData = initializedPagedListBuilder(config).build().also {
            it.observe(viewLifecycleOwner, Observer<PagedList<UsersModel.UsersModelItem>> { pagedList ->
                adapter.submitList(pagedList)
            })
        }

    }

    /**
     * Initialize page list builder
     */
    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, UsersModel.UsersModelItem> {

        val database = UsersDb.create(requireActivity())
        val livePageListBuilder = LivePagedListBuilder(
            database.usersDao().getUsers(),
            config)
        livePageListBuilder.setBoundaryCallback(UsersBoundaryCallback(database))
        return livePageListBuilder
    }
}