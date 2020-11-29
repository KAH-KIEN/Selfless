package com.android.example.eventactivity.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(supportFragmentManager: FragmentManager):
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    //hold the fragment
    private val mFragmentList = ArrayList<Fragment>()
    //hold the title
    private val mFragmentTitle = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitle[position]
    }

    fun addFragment(fragment: Fragment, title: String){
        //a funcion to add the fragment and title into the list
        mFragmentList.add(fragment)
        mFragmentTitle.add(title)
    }

}