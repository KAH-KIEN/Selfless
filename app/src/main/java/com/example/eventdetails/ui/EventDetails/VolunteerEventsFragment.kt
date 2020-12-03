package com.example.eventdetails.ui.EventDetails

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//Credits to kimkevin : CachePot

class VolunteerEventsFragment : Fragment() {

    companion object {
        fun newInstance() = VolunteerEventsFragment()
    }

    private lateinit var viewModel: VolunteerEventsViewModel
    val eventID: String = CachePot.getInstance().pop(String::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.volunteer_events_fragment, container, false)
        val buttonComplete = root.findViewById<Button>(R.id.buttonCompleteVolunteer)
        Log.i("ButtonComplete", "$buttonComplete")
        val ref = FirebaseDatabase.getInstance().reference.child("Events").child("$eventID")
        var whatsAppLink :String
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                whatsAppLink = snapshot.child("eventWhatsapp").value.toString()
                val textViewWhatsApp: TextView = root.findViewById(R.id.textViewWhatsAppVolunteer)
                textViewWhatsApp.text = whatsAppLink
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        buttonComplete.setOnClickListener {
            Log.i("OnClickButtonComplete", "Clicked")
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.fragment_qrscanner)

        }

        val textWhatsApp = root.findViewById(R.id.textViewWhatsAppVolunteer) as TextView
        textWhatsApp.movementMethod = LinkMovementMethod.getInstance()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VolunteerEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}