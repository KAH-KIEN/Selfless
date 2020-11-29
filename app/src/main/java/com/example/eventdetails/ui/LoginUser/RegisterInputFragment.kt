package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventdetails.R

class RegisterInput : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false);
        val root = inflater.inflate(R.layout.fragment_register_input, container, false)
        val buttonCodeNext : Button = root.findViewById(R.id.buttonCodeNext)
        val editTextCCode: TextView = root.findViewById(R.id.editTextCCode)
        buttonCodeNext.setOnClickListener {
            if(editTextCCode.text.trim().isEmpty())
                Toast.makeText(getActivity(), "Input Required", Toast.LENGTH_SHORT).show()
            else{
                requireView().findNavController().navigate(R.id.navigation_registerInput2)
            }

        }


        return root
    }
}