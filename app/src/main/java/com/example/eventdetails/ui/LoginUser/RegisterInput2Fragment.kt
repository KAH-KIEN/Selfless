package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.databinding.FragmentLoginBinding
import com.example.eventdetails.databinding.FragmentRegisterBinding
import com.example.eventdetails.databinding.FragmentRegisterInput2Binding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class RegisterInput2 : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var registerInput2ViewModel: RegisterInput2ViewModel
    private var db = FirebaseDatabase.getInstance().reference
    private lateinit var binding: FragmentRegisterInput2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth

        setHasOptionsMenu(false);
        registerInput2ViewModel =
            ViewModelProvider(this).get(RegisterInput2ViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_register_input2, container, false)
        val textInput_name: TextInputLayout = root.findViewById(R.id.textInput_name)
        val textInput_birthday: TextInputLayout = root.findViewById(R.id.textInput_birthday)
        val textInput_password: TextInputLayout = root.findViewById(R.id.textInput_password)
        val textInput_phone: TextInputLayout = root.findViewById(R.id.textInput_phone)
        val textInput_email: TextInputLayout = root.findViewById(R.id.textInput_email)
        val buttonProfileNext:Button = root.findViewById(R.id.buttonProfileNext)

        buttonProfileNext.setOnClickListener {
            if (textInput_name.getEditText()?.getText().toString().trim()
                    .isEmpty() || textInput_birthday.getEditText()!!.text.isEmpty() || textInput_phone.getEditText()!!.text.isEmpty() || textInput_password.getEditText()!!.text.isEmpty() ||textInput_email.getEditText()!!.text.isEmpty()
            )
                Toast.makeText(getActivity(), "Input Required", Toast.LENGTH_SHORT).show()
            else {
                auth.createUserWithEmailAndPassword(textInput_email.getEditText()!!.text.trim().toString(),textInput_password.getEditText()!!.text.trim().toString() )
                    .addOnCompleteListener(requireActivity()){
                            task ->
                        if(task.isSuccessful){
                            var userID = auth.getCurrentUser()?.uid

                            var w:String = textInput_email.getEditText()?.getText().toString().trim()

                            var name = textInput_name.getEditText()!!.text.toString()
                            var birthday = textInput_birthday.getEditText()!!.text.toString()
                            var contact = textInput_phone.getEditText()!!.text.toString()
                            var email = textInput_email.getEditText()!!.text.toString()

                            db.child("User").child("$userID").setValue(User(name, birthday, contact, email))

                            Toast.makeText(getActivity(), "Register Successful!", Toast.LENGTH_SHORT).show()
                            requireView().findNavController().navigate(R.id.navigation_profilePic)
                        }else{

                            Toast.makeText(getActivity(), "Register Failed!", Toast.LENGTH_SHORT).show()
                        }

                    }

            }
        }

        return root
    }

}