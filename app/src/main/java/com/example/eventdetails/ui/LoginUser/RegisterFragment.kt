package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.databinding.FragmentRegisterBinding

class Register : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.buttonRegisterEmail.setOnClickListener {
            requireView().findNavController().navigate(R.id.navigation_registerInput2)
        }

        binding.buttonGoLogin.setOnClickListener {
            requireView().findNavController().navigate(R.id.navigation_login)
        }
        return view
    }
}