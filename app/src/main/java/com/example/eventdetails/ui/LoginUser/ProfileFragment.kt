package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.eventdetails.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private var db = FirebaseDatabase.getInstance().reference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val buttonEditProfile : Button = root.findViewById(R.id.buttonEditProfile)
        val buttonSignout : Button = root.findViewById(R.id.buttonSignout)
        val textViewEmail:TextView = root.findViewById(R.id.textViewEmail)
        val textViewName: TextView = root.findViewById(R.id.textViewName)
        val textViewBirthday: TextView = root.findViewById(R.id.textViewBirthday)
        val textViewContact: TextView = root.findViewById(R.id.textViewContact)

        buttonEditProfile.setOnClickListener {
            requireView().findNavController().navigate(R.id.navigation_editProfile)
        }

        buttonSignout.setOnClickListener{
            auth.signOut()
            Toast.makeText(getActivity(), "User signed out", Toast.LENGTH_SHORT).show()
            requireParentFragment().findNavController().navigate(R.id.navigation_login)
        }

        val user = auth.currentUser
        if (user != null) {
            var userID = auth.getCurrentUser()?.uid

            db.child("User").child("$userID").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val model= ViewModelProviders.of(requireActivity()).get(Communicator2::class.java)

                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val birthday = dataSnapshot.child("birthday").getValue(String::class.java)
                    val contact = dataSnapshot.child("contact").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)

                    //User(name.toString(), birthday.toString(), contact.toString(), email.toString())
                    //var user = User()

                    textViewName.text = name
                    Log.w("1", "$name")
                    textViewBirthday.text = birthday
                    textViewContact.text = contact
                    textViewEmail.text = email
/*
                    model.name.observe(viewLifecycleOwner,
                        { o -> textViewName.setText(o!!.toString()) })

                    model.birthday.observe(viewLifecycleOwner,
                        { o -> textViewBirthday.setText(o!!.toString())  })

                    model.contact.observe(viewLifecycleOwner,
                        { o -> textViewContact.setText(o!!.toString()) })

                    model.email.observe(viewLifecycleOwner,
                        { o -> textViewEmail.setText(o!!.toString()) })*/

                    model!!.setMsgCommunicator(textViewName.text.toString(),textViewBirthday.text.toString(),textViewContact.text.toString(),textViewEmail.text.toString())

                    Log.d("Get data", "Get value")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("Get data", "Failed to read value.", error.toException())
                }
            })
        }


        setHasOptionsMenu(true)
        return root
    }

    fun signOutt(){
        auth.signOut()
        Toast.makeText(getActivity(), "User signed out", Toast.LENGTH_SHORT).show()
        requireView().findNavController().navigate(R.id.navigation_login)
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