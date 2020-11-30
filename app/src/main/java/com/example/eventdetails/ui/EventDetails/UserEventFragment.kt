package com.example.eventdetails.ui.EventDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class UserEventFragment : Fragment() {

    companion object {
        fun newInstance() = UserEventFragment()
    }

    private lateinit var viewModel: UserEventViewModel
    val eventID: String = CachePot.getInstance().pop(String::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.user_event_fragment, container, false)
        val buttonRegister = root.findViewById<Button>(R.id.buttonRegister)
        val auth = Firebase.auth
        val user = auth.currentUser
        val userID = user?.uid
        var listLength : Int = 0
        val myRef = FirebaseDatabase.getInstance().reference.child("User").child("$userID").child("VolunteeredEvents")
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children)
                {
                    listLength++
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        Log.i("RegisterButton","$buttonRegister")
        buttonRegister.setOnClickListener {
            Log.i("OnClickRegisterButton","$buttonRegister")
            if (user != null) {
                val userID = auth.currentUser?.uid
                myRef.child("$listLength").setValue(eventID)
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserEventViewModel::class.java)
        // TODO: Use the ViewModel
    }

}