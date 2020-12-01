package com.example.eventdetails.ui.MyEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.android.example.eventactivity.fragments.adapters.ViewPagerAdapter
import com.example.eventdetails.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = Firebase.auth
        val user = auth.currentUser
        if(user == null)
        {
            Toast.makeText(activity, "User not logged in", Toast.LENGTH_SHORT).show()
            requireView().findNavController().navigate(R.id.navigation_login)

        }
    }



}