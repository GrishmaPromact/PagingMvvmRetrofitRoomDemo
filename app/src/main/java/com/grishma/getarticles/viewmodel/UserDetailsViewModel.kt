package com.grishma.getarticles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grishma.getarticles.model.Articles

/**
 * User Details view model class
 */
class UserDetailsViewModel : ViewModel() {
    private val article: MutableLiveData<Articles.ArticlesItem> = MutableLiveData<Articles.ArticlesItem>()

    //get articles
    fun getArticle(): MutableLiveData<Articles.ArticlesItem> {
        return article
    }

}
