package com.example.eventdetails.ui.MyEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventdetails.R

class LoginFragment : Fragment() {

    /*private lateinit var myEventsViewModel: MyEventsViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false);
        /*myEventsViewModel =
            ViewModelProvider(this).get(MyEventsViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_notifications)
        myEventsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}