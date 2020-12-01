@file:Suppress("DEPRECATION")

package com.example.eventdetails.ui.EventDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class EditEventsFragment : Fragment() {

    companion object {
        fun newInstance() = EditEventsFragment()
    }

    val eventID: String = CachePot.getInstance().pop(String::class.java)
    private lateinit var viewModel: EditEventsViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i("OnCreateView","View Created")
        /*val root = inflater.inflate(R.layout.fragment_eventdetails, container, false)*//*
        val textViewTitle:TextView = root.findViewById(R.id.textViewTitle)
        val textViewDate:TextView = root.findViewById(R.id.textViewDate)
        val textViewTime:TextView = root.findViewById(R.id.textViewTime)
        val textViewLocation:TextView = root.findViewById(R.id.textViewLocation)
        val textViewPhoneNum:TextView = root.findViewById(R.id.textViewPhoneNum)
        val textViewDesc:TextView = root.findViewById(R.id.textViewDesc)*/

        val model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)

        /*model.title.observe(viewLifecycleOwner,
            { o -> textViewTitle.text = o!!.toString() })

        model.date.observe(viewLifecycleOwner,
            { o -> textViewDate.text = o!!.toString() })

        model.time.observe(viewLifecycleOwner,
            { o -> textViewTime.text = o!!.toString() })

        model.location.observe(viewLifecycleOwner,
            { o -> textViewLocation.text = o!!.toString() })

        model.phoneNum.observe(viewLifecycleOwner,
            { o -> textViewPhoneNum.text = o!!.toString() })

        model.desc.observe(viewLifecycleOwner,
            { o -> textViewDesc.text = o!!.toString() })*/

        val root= inflater.inflate(R.layout.edit_events_fragment, container, false)
        val root2 = inflater.inflate(R.layout.fragment_eventdetails, container, false)

        val editTextTitle:EditText = root.findViewById(R.id.editTextTitle)
        val textViewTitle:TextView = root2.findViewById(R.id.textViewTitle)
        editTextTitle.setText(textViewTitle.text)

        val editTextDate:TextView = root.findViewById(R.id.editTextDate)
        val textViewDate:TextView = root2.findViewById(R.id.textViewDate)
        editTextDate.text = textViewDate.text

        val editTextTime:TextView = root.findViewById(R.id.editTextTime)
        val textViewTime:TextView = root2.findViewById(R.id.textViewTime)
        editTextTime.text = textViewTime.text

        val editTextLocation:EditText = root.findViewById(R.id.editTextLocation)
        val textViewLocation:TextView = root2.findViewById(R.id.textViewLocation)
        editTextLocation.setText(textViewLocation.text)

        val editTextPhoneNum:EditText = root.findViewById(R.id.editTextPhoneNum)
        val textViewPhoneNum:TextView = root2.findViewById(R.id.textViewPhoneNum)
        editTextPhoneNum.setText(textViewPhoneNum.text)

        val editTextDesc:EditText = root.findViewById(R.id.editTextDesc)
        val textViewDesc:TextView = root2.findViewById(R.id.textViewDesc)
        editTextDesc.setText(textViewDesc.text)

        model.title.observe(viewLifecycleOwner,
            { o -> editTextTitle.setText(o!!.toString()) })

        model.date.observe(viewLifecycleOwner,
            { o -> editTextDate.text = o!!.toString() })

        model.time.observe(viewLifecycleOwner,
            { o -> editTextTime.text = o!!.toString() })

        model.location.observe(viewLifecycleOwner,
            { o -> editTextLocation.setText(o!!.toString()) })

        model.phoneNum.observe(viewLifecycleOwner,
            { o -> editTextPhoneNum.setText(o!!.toString()) })

        model.desc.observe(viewLifecycleOwner,
            { o -> editTextDesc.setText(o!!.toString())})


        val buttonEditSave: Button = root.findViewById(R.id.buttonEditSave)
        var editTextWhatsApp: EditText = root.findViewById(R.id.editTextWhatsApp)
        val myRef = FirebaseDatabase.getInstance().reference.child("Events").child("$eventID")
        myRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val textViewSlotNum: TextView = root.findViewById(R.id.textViewSlotNum)
                textViewSlotNum.text = snapshot.child("eventRegister").value.toString() + "/" +snapshot.child("eventSlot").value.toString()
                editTextWhatsApp.setText(snapshot.child("eventWhatsapp").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        buttonEditSave.setOnClickListener {
            model!!.setMsgCommunicator(editTextTitle.text.toString(),editTextDate.text.toString(),editTextTime.text.toString(),editTextLocation.text.toString(),editTextPhoneNum.text.toString(),editTextDesc.text.toString())
            myRef.child("eventTitle").setValue(editTextTitle.text.toString())
            myRef.child("eventDate").setValue(editTextDate.text.toString())
            myRef.child("eventTime").setValue(editTextTime.text.toString())
            myRef.child("eventLocation").setValue(editTextLocation.text.toString())
            myRef.child("eventDescription").setValue(editTextDesc.text.toString())
            myRef.child("eventWhatsapp").setValue(editTextWhatsApp.text.toString())
            /*textViewTitle.text = editTextTitle.text
            textViewDate.text = editTextDate.text
            textViewTime.text = editTextTime.text
            textViewLocation.text = editTextLocation.text
            textViewDesc.text = editTextDesc.text*/
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.navigation_eventDetails)
        }

        val imageButtonEditTime : ImageButton = root.findViewById(R.id.imageButtonEditTime)

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            editTextTime.text = (SimpleDateFormat("HH:mm").format(cal.time))
        }

        imageButtonEditTime.setOnClickListener() {

            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        val imageButtonEditDate : ImageButton = root.findViewById(R.id.imageButtonEditDate)


        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            editTextDate.text = sdf.format(cal.time)
        }

        imageButtonEditDate.setOnClickListener {
            context?.let { it1 ->
                DatePickerDialog(it1, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }


        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}