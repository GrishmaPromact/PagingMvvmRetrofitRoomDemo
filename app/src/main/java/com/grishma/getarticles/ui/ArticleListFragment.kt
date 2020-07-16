package com.grishma.getarticles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grishma.getarticles.R
import com.grishma.getarticles.adapter.ArticlesPageListAdapter
import com.grishma.getarticles.db.ArticlesDb
import com.grishma.getarticles.model.Articles
import com.grishma.getarticles.utils.articles.ArticlesBoundaryCallback
import com.grishma.getarticles.viewmodel.UserDetailsViewModel

/**
 * Articles List Fragment to display Articles
 */
class ArticleListFragment : Fragment(){
    private var detailsViewModel: UserDetailsViewModel? = null
    private lateinit var articlesRecyclerView1: RecyclerView
    private val adapter = ArticlesPageListAdapter(object : ArticlesPageListAdapter.ItemClickListener{
        override fun OnItemClick(articlesItem: Articles.ArticlesItem?) {
            detailsViewModel?.getArticle()?.postValue(articlesItem)
            if (!detailsViewModel?.getArticle()?.hasActiveObservers()!!) {
                // Create fragment and give it an argument specifying the article it should show
                val detailsFragment = UserDetailsFragment()
                val transaction =
                    activity!!.supportFragmentManager.beginTransaction()
                // Replace whatever is in the fragmentsContainer view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragmentsContainer, detailsFragment)
                transaction.addToBackStack(null)
                // Commit the transaction
                transaction.commit()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_articles, container, false)
        articlesRecyclerView1 = view.findViewById(R.id.articlesRecyclerView) as RecyclerView
        initializeList()
        return view
    }

    private fun initializeList() {
        articlesRecyclerView1.layoutManager = LinearLayoutManager(this.context)
        articlesRecyclerView1.adapter = adapter

        //set paging configs
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()

        //observe article list and submit it into adapter
        val liveData = initializedPagedListBuilder(config).build().also {
            it.observe(viewLifecycleOwner, Observer<PagedList<Articles.ArticlesItem>> { pagedList ->
                adapter.submitList(pagedList)
            })
        }

        //Articles Details view model class
        detailsViewModel =
            ViewModelProvider(activity!!).get(UserDetailsViewModel::class.java)
    }

    /**
     * Initialize page list builder
     */
    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Articles.ArticlesItem> {

        val database = ArticlesDb.create(requireActivity())
        val livePageListBuilder = LivePagedListBuilder(
            database.articlesDao().getArticles(),
            config)
        livePageListBuilder.setBoundaryCallback(
            ArticlesBoundaryCallback(
                database
            )
        )
        return livePageListBuilder
    }
}