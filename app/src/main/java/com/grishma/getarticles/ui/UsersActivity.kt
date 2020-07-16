package com.grishma.getarticles.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grishma.getarticles.R
import kotlinx.android.synthetic.main.activity_articles.*

/**
 * User list Activity
 */
class UsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        if (fragmentsContainer != null) {
            if (savedInstanceState != null) {
                return
            }
            // Create a new Fragment to be placed in the activity layout
            val usersListFragment = UsersListFragment()

            // Intent, pass the Intent's extras to the fragment as arguments
            usersListFragment.arguments = intent.extras

            // Add the fragment to the 'fragmentsContainer' FrameLayout
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentsContainer, usersListFragment).commit()
        }
    }
}