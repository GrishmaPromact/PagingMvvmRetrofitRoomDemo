package com.grishma.getarticles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grishma.getarticles.R
import com.grishma.getarticles.viewmodel.UserDetailsViewModel
import kotlinx.android.synthetic.main.fragment_user_details.view.*

/**
 * User Details fragment
 */
class UserDetailsFragment : Fragment() {
    private var viewModel: UserDetailsViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class.
        val view: View =
            inflater.inflate(R.layout.fragment_user_details, container, false)
        //get user details view model
        viewModel = ViewModelProvider(activity!!).get(UserDetailsViewModel::class.java)

        //Display user details
        viewModel!!.getArticle().observe(viewLifecycleOwner, Observer { t ->
            if(t.user.isNotEmpty()) {
                if (t.user[0].avatar.isNotEmpty()) {
                    Glide.with(view.context).load(t.user[0].avatar)
                        .apply(RequestOptions().circleCrop())
                        .placeholder(R.drawable.user_placeholder)
                        .into(view.ivUserProfile)
                } else{
                    view.ivUserProfile.visibility = View.GONE
                }
                if (t.user[0].name.isNotEmpty()) {
                    view.tvFullName.visibility = View.VISIBLE
                    view.tvFullName.text = t.user[0].name
                } else {
                    view.tvFullName.visibility = View.GONE
                }

                if (t.user[0].designation.isNotEmpty()) {
                    view.tvUserDesignationDetails.visibility = View.VISIBLE
                    view.tvUserDesignationDetails.text = t.user[0].designation
                } else {
                    view.tvUserDesignationDetails.visibility = View.GONE
                }

                if (t.user[0].city.isNotEmpty()) {
                    view.tvCity.visibility = View.VISIBLE
                    view.tvCity.text = t.user[0].city
                } else {
                    view.tvCity.visibility = View.GONE
                }

                if (t.content.isNotEmpty()) {
                    view.tvDesc.visibility = View.VISIBLE
                    view.tvDesc.text = t.content
                } else {
                    view.tvDesc.visibility = View.GONE
                }

            }
        })
        return view
    }

}