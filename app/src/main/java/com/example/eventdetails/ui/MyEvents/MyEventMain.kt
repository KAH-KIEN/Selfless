package com.example.eventdetails.ui.MyEvents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.android.example.eventactivity.fragments.CalendarFragment
import com.android.example.eventactivity.fragments.ListFragment
import com.android.example.eventactivity.fragments.MapFragment
import com.android.example.eventactivity.fragments.adapters.ViewPagerAdapter
import com.example.eventdetails.R
import com.example.eventdetails.ui.EventDetails.OrganiserEventsFragment
import com.example.eventdetails.ui.EventDetails.VolunteerEventsFragment
import com.google.android.material.tabs.TabLayout

class MyEventMain : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_myevents, container, false)
        val viewPager: ViewPager = root.findViewById(R.id.view_pager2)
        val tabs: TabLayout = root.findViewById(R.id.tabs2)
        val adapter = ViewPagerAdapter(childFragmentManager)


        adapter.addFragment(OrganiserEventListFragment(), "Organised Events")
        adapter.addFragment(VolunteerEventListFragment(), "Volunteer Events")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_calendar_today_white_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_format_list_bulleted_24)

        return root
    }



}