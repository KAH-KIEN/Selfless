package com.example.eventdetails.ui.Events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.android.example.eventactivity.fragments.CalendarFragment
import com.android.example.eventactivity.fragments.ListFragment
import com.android.example.eventactivity.fragments.MapFragment
import com.android.example.eventactivity.fragments.adapters.ViewPagerAdapter
import com.example.eventdetails.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class EventMain : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_event_main, container, false)
        val viewPager: ViewPager = root.findViewById(R.id.view_pager)
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        val adapter = ViewPagerAdapter(childFragmentManager)
        val floatingActionButtonCreate: FloatingActionButton = root.findViewById(R.id.floatingActionButtonCreate)

        adapter.addFragment(CalendarFragment(), "Calendar")
        adapter.addFragment(ListFragment(), "List")
        adapter.addFragment(MapFragment(), "Map")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_calendar_today_white_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_format_list_bulleted_24)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_map_24)

        floatingActionButtonCreate.setOnClickListener {
            requireView().findNavController().navigate(R.id.fragment_create_event)
        }

        return root
    }



}