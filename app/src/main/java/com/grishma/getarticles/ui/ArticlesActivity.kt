package com.grishma.getarticles.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grishma.getarticles.R
import kotlinx.android.synthetic.main.activity_articles.*

/**
 * ArticleListActivity
 */
class ArticlesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        if (fragmentsContainer != null) {
            if (savedInstanceState != null) {
                return
            }
            // Create a new Fragment to be placed in the activity layout
            val articleListFragment = ArticleListFragment()
            // Intent, pass the Intent's extras to the fragment as arguments
            articleListFragment.arguments = intent.extras

            // Add the fragment to the 'fragmentsContainer' FrameLayout
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentsContainer, articleListFragment).commit()
        }
    }
}