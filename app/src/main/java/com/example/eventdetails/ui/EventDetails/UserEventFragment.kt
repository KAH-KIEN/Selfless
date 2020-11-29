package com.example.eventdetails.ui.EventDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventdetails.R

class UserEventFragment : Fragment() {

    companion object {
        fun newInstance() = UserEventFragment()
    }

    private lateinit var viewModel: UserEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserEventViewModel::class.java)
        // TODO: Use the ViewModel
    }

}