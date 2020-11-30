package com.example.eventdetails.ui.home

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        auth = Firebase.auth
        setHasOptionsMenu(false);
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val buttonOrganize: Button = root.findViewById(R.id.buttonOrganize)
        val buttonVolunteer: Button = root.findViewById(R.id.buttonVolunteer)

        buttonVolunteer.setOnClickListener{
            requireView().findNavController().navigate(R.id.navigation_eventMain)
        }
        buttonOrganize.setOnClickListener {
            requireView().findNavController().navigate(R.id.fragment_create_event)
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user == null){
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show()
            requireView().findNavController().navigate(R.id.navigation_login)
        }
    }
}