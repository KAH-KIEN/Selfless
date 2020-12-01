package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = Firebase.auth

        setHasOptionsMenu(false);

        binding.textViewRegister.setOnClickListener {
            requireView().findNavController().navigate(R.id.navigation_register)
        }

        binding.buttonLogin.setOnClickListener {
            if(binding.editTextEmailAddress.text.trim().isEmpty()||binding.editTextLoginPassword.text.trim().isEmpty())
                Toast.makeText(getActivity(), "Input Required", Toast.LENGTH_SHORT).show()
            else{
                auth.signInWithEmailAndPassword(binding.editTextEmailAddress.editableText.toString(), binding.editTextLoginPassword.editableText.toString())
                    .addOnCompleteListener(){
                            task ->
                        if(task.isSuccessful){
                            Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show()
                            requireView().findNavController().navigate(R.id.navigation_home)
                        }else{
                            Toast.makeText(getActivity(), "Login Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user != null){
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show()
            requireView().findNavController().navigate(R.id.navigation_login)
        }
    }
}