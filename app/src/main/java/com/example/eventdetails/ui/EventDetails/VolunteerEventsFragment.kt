package com.example.eventdetails.ui.EventDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventdetails.R

class VolunteerEventsFragment : Fragment() {

    companion object {
        fun newInstance() = VolunteerEventsFragment()
    }

    private lateinit var viewModel: VolunteerEventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.volunteer_events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VolunteerEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}