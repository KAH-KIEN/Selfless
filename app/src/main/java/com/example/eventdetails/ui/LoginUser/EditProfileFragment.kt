package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class EditProfileFragment : Fragment() {
    private var db = FirebaseDatabase.getInstance().reference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false);
        auth = Firebase.auth
        val root = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val root2 = inflater.inflate(R.layout.fragment_profile, container, false)

        val model= ViewModelProviders.of(requireActivity()).get(Communicator2::class.java)

        val editTextName: EditText = root.findViewById(R.id.editTextName)
        val editTextBirthday: EditText = root.findViewById(R.id.editTextBirthday)
        val editTextPhone: EditText = root.findViewById(R.id.editTextPhone)
        val editTextProfileEmail: TextView = root.findViewById(R.id.editTextProfileEmail)

        model.name.observe(viewLifecycleOwner,
            { o -> editTextName.setText(o!!.toString()) })

        model.birthday.observe(viewLifecycleOwner,
            { o -> editTextBirthday.setText(o!!.toString())  })

        model.contact.observe(viewLifecycleOwner,
            { o -> editTextPhone.setText(o!!.toString()) })

        model.email.observe(viewLifecycleOwner,
            { o -> editTextProfileEmail.setText(o!!.toString()) })

        var buttonEditProfileDone : Button = root.findViewById(R.id.buttonEditProfileDone)

        buttonEditProfileDone.setOnClickListener {
            var user = auth.currentUser
            if (user != null) {
                var userID = auth.getCurrentUser()?.uid
                db.child("User").child("$userID").child("name").setValue(editTextName.text.toString())
                db.child("User").child("$userID").child("birthday").setValue(editTextBirthday.text.toString())
                db.child("User").child("$userID").child("contact").setValue(editTextPhone.text.toString())

            }
            Toast.makeText(getActivity(), "Changes saved!", Toast.LENGTH_SHORT).show()

            requireView().findNavController().navigate(R.id.navigation_profile)
        }
        return root
    }

}