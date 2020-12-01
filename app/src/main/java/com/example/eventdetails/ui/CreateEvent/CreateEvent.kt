package com.example.eventdetails.ui.CreateEvent

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventWrite
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.*


class CreateEvent : Fragment() {
    lateinit var editEventTitle: TextInputLayout
    lateinit var editEventDate:TextInputLayout
    lateinit var editEventTime: TextInputLayout
    lateinit var editEventLocation: TextInputLayout
    lateinit var editEventContact:TextInputLayout
    lateinit var editEventDescription: TextInputLayout
    lateinit var editEventWhatappsLink:TextInputLayout
    lateinit var editEventSlot: TextInputLayout
    lateinit var buttonCreate:Button
    lateinit var eventDate:EditText
    lateinit var eventTime:EditText
    private var mDateSetListener: OnDateSetListener? = null
    private var mTimeSetListener: TimePickerDialog.OnTimeSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_event, container, false)
        editEventTitle = root.findViewById(R.id.textInput_eventTitle)
        editEventDate = root.findViewById(R.id.textInput_eventDate)
        editEventTime = root.findViewById(R.id.textInput_eventTime)
        editEventLocation = root.findViewById(R.id.textInput_eventLocation)
        editEventContact = root.findViewById(R.id.textInput_eventContact)
        editEventDescription = root.findViewById(R.id.textInput_eventDescription)
        editEventWhatappsLink = root.findViewById(R.id.textInput_eventWhatsappLink)
        editEventSlot = root.findViewById(R.id.textInput_eventSlot)
        buttonCreate = root.findViewById(R.id.buttonCreate)
        eventDate = root.findViewById(R.id.eventDate)
        eventTime = root.findViewById(R.id.eventTime)

        eventDate.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val dialog = DatePickerDialog(
                    requireActivity(),
                    mDateSetListener,
                    year, month, day)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                dialog.show()
            }
        })

        mDateSetListener = object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(datePicker: DatePicker, year:Int, month:Int, day:Int) {
                //Jan is 0, Dec is 11
                var months = month + 1
                val date = "$day" + "/" + "$months" + "/" + "$year"
                eventDate.text = Editable.Factory.getInstance().newEditable(date)
            }
        }

        eventTime.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val cal = Calendar.getInstance()
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val minutes = cal.get(Calendar.MINUTE)
                val picker = TimePickerDialog(
                    requireActivity(),
                    mTimeSetListener,
                    hour, minutes, false
                )
                picker.show()
            }
        })

        mTimeSetListener = object: TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                val am_pm:String
                var hour: Int = hourOfDay
                if (hourOfDay > 12)
                {
                    am_pm = "PM"
                    hour = hourOfDay - 12
                }
                else
                {
                    am_pm = "AM"
                }
                val time = "$hour" + ":" + "$minute" + " $am_pm"
                eventTime.text = Editable.Factory.getInstance().newEditable(time)
            }
        }

        buttonCreate.setOnClickListener{
            createEvent()
        }
        return root
    }

    private fun createEvent(){
        val eventTitle = editEventTitle.getEditText()?.getText().toString().trim()
        val eventDate = editEventDate.getEditText()?.getText().toString().trim()
        val eventTime = editEventTime.getEditText()?.getText().toString().trim()
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

        val events = EventWrite(
            eventID.toString(),
            eventTitle,
            eventDate,
            eventTime,
            eventLocation,
            eventContact,
            eventDescription,
            eventWhatsapp,
            eventSlot.toInt(),
            eventRegister
        )

        ref.child(eventID.toString()).setValue(events).addOnCompleteListener{
            Toast.makeText(requireActivity(), "Event Created", Toast.LENGTH_SHORT).show()
            requireView().findNavController().navigate(R.id.navigation_home)
        }

        val auth = Firebase.auth
        val user = auth.currentUser
        val userID = user?.uid
        var listLength : Int = 0
        val myRef = FirebaseDatabase.getInstance().reference.child("User")
                .child("$userID").child("OrganisedEvents")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children)
                {
                    listLength++
                }
                myRef.child("$listLength").setValue(eventID.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}