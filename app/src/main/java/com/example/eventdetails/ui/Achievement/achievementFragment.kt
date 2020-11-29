package com.example.eventdetails.ui.Achievement

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventdetails.R

class achievementFragment : Fragment() {

    companion object {
        fun newInstance() = achievementFragment()
    }

    private lateinit var viewModel: AchievementViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.achievement_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AchievementViewModel::class.java)
        // TODO: Use the ViewModel
    }

}