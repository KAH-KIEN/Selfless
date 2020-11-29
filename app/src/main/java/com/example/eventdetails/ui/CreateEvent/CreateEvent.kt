package com.example.eventdetails.ui.CreateEvent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventWrite
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class CreateEvent : Fragment() {
    lateinit var editEventTitle: TextInputLayout
    lateinit var editEventDate:TextInputLayout
    lateinit var editEventTime: TextInputLayout
    lateinit var editEventDuration:TextInputLayout
    lateinit var editEventLocation: TextInputLayout
    lateinit var editEventContact:TextInputLayout
    lateinit var editEventDescription: TextInputLayout
    lateinit var editEventWhatappsLink:TextInputLayout
    lateinit var editEventSlot: TextInputLayout
    lateinit var buttonCreate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_event, container, false)
        editEventTitle = root.findViewById(R.id.textInput_eventTitle)
        editEventDate = root.findViewById(R.id.textInput_eventDate)
        editEventTime = root.findViewById(R.id.textInput_eventTime)
        editEventDuration = root.findViewById(R.id.textInput_eventDuration)
        editEventLocation = root.findViewById(R.id.textInput_eventLocation)
        editEventContact = root.findViewById(R.id.textInput_eventContact)
        editEventDescription = root.findViewById(R.id.textInput_eventDescription)
        editEventWhatappsLink = root.findViewById(R.id.textInput_eventWhatsappLink)
        editEventSlot = root.findViewById(R.id.textInput_eventSlot)
        buttonCreate = root.findViewById(R.id.buttonCreate)

        buttonCreate.setOnClickListener{
//            val eventTitle = editEventTitle.getEditText()!!.text.trim()
//            Toast.makeText(requireActivity(), "You clicked on $eventTitle", Toast.LENGTH_SHORT).show()
            createEvent()
        }
        return root
    }

    private fun createEvent(){
        val eventTitle = editEventTitle.getEditText()?.getText().toString().trim()
        val eventDate = editEventDate.getEditText()?.getText().toString().trim()
        val eventTime = editEventTime.getEditText()?.getText().toString().trim()
        val eventDuration = editEventDuration.getEditText()?.getText().toString().trim()
        val eventLocation = editEventLocation.getEditText()?.getText().toString().trim()
        val eventContact = editEventContact.getEditText()?.getText().toString().trim()
        val eventDescription = editEventDescription.getEditText()?.getText().toString().trim()
        val eventWhatsapp = editEventWhatappsLink.getEditText()?.getText().toString().trim()
        val eventSlot = editEventSlot.getEditText()?.getText().toString().trim()
        val eventRegister: Int = 0


        if(eventTitle.isEmpty()){
            editEventTime.error = "Please enter a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Events")
        val eventID = ref.push().key

        val events = EventWrite(eventID.toString(), eventTitle, eventDate, eventTime, eventDuration.toInt(), eventLocation,
                eventContact, eventDescription, eventWhatsapp, eventSlot.toInt(), eventRegister)

        ref.child(eventID.toString()).setValue(events).addOnCompleteListener{
            Toast.makeText(requireActivity(), "Event Created", Toast.LENGTH_SHORT).show()
            requireView().findNavController().navigate(R.id.navigation_home)
        }



    }


}