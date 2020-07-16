package com.grishma.getarticles.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grishma.getarticles.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //On click of get articles btn
        btnArticles.setOnClickListener {
            var intent = Intent(this, ArticlesActivity::class.java)
            startActivity(intent)
        }

        //On click of get users btn
        btnUsers.setOnClickListener {
            var intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }
    }
}