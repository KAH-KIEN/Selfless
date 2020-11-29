package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        setHasOptionsMenu(false);

        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val model= ViewModelProviders.of(requireActivity()).get(Communicator2::class.java)
        val buttonLogin : TextView = root.findViewById(R.id.buttonLogin)
        val editTextEmailAddress: TextView = root.findViewById(R.id.editTextEmailAddress)
        val editTextLoginPassword: TextView = root.findViewById(R.id.editTextLoginPassword)
        val textViewForgotPW: TextView = root.findViewById(R.id.textViewForgotPW)

/*        model.email.observe(viewLifecycleOwner,
            { o -> editTextEmailAddress.setText(o!!.toString()) })

        model!!.setEmailCommunicator(editTextEmailAddress.text.toString())*/

        val textViewRegister : TextView = root.findViewById(R.id.textViewRegister)
        textViewRegister.setOnClickListener {
            requireView().findNavController().navigate(R.id.navigation_register)
        }

        textViewForgotPW.setOnClickListener{
            requireView().findNavController().navigate(R.id.navigation_home)
            //TODO:confirmation email
        }
        buttonLogin.setOnClickListener {
            if(editTextEmailAddress.text.trim().isEmpty()||editTextLoginPassword.text.trim().isEmpty())
                Toast.makeText(getActivity(), "Input Required", Toast.LENGTH_SHORT).show()
            else{
                auth.signInWithEmailAndPassword(editTextEmailAddress.editableText.toString(), editTextLoginPassword.editableText.toString())
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
        return root
    }
}