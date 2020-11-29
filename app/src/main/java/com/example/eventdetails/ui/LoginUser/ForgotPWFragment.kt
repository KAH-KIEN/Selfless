package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventdetails.R

class ForgotPWFragment : Fragment() {

    private lateinit var forgotPWViewModel: ForgotPWViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false);
        forgotPWViewModel =
            ViewModelProvider(this).get(ForgotPWViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_forgot_p_w, container, false)
        //loginViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})
        return root
    }
}