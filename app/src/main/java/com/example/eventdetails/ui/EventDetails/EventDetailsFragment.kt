package com.example.eventdetails.ui.EventDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.android.example.eventactivity.fragments.adapters.RecyclerAdapter
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventRead
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


class EventDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var eventDetailsViewModel: EventDetailsViewModel
    val eventID: String = CachePot.getInstance().pop(String::class.java)

    /*override fun onCreate(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_eventdetails, container, false)
        val textViewTitle = root.findViewById(R.id.textViewTitle)
        val textViewDate = root.findViewById(R.id.textViewDate)
        val textViewTime = root.findViewById(R.id.textViewTime)
        val textViewLocation = root.findViewById(R.id.textViewLocation)
        val textViewPhoneNum = root.findViewById(R.id.textViewPhoneNum)
        val textViewDesc = root.findViewById(R.id.textViewDesc)

        val model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
        model!!.setMsgCommunicator(textViewTitle.text.toString(),textViewDate.text.toString(),textViewTime.text.toString(),textViewLocation.text.toString(),textViewPhoneNum.text.toString(),textViewDesc.text.toString())

    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Event Details Fragment", "Created")
        CachePot.getInstance().push(eventID)
        setHasOptionsMenu(true);
        eventDetailsViewModel =
            ViewModelProvider(this).get(EventDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_eventdetails, container, false)
        val textViewTitle:TextView = root.findViewById(R.id.textViewTitle)
        val textViewDate:TextView = root.findViewById(R.id.textViewDate)
        val textViewTime:TextView = root.findViewById(R.id.textViewTime)
        val textViewLocation:TextView = root.findViewById(R.id.textViewLocation)
        val textViewPhoneNum:TextView = root.findViewById(R.id.textViewPhoneNum)
        val textViewDesc:TextView = root.findViewById(R.id.textViewDesc)
        val textViewSlotNum: TextView = root.findViewById(R.id.textViewSlotNum)
        val model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
        val stringList: MutableList<String> = mutableListOf()

        Log.i("Lmao", "$eventID")
        val myRef = FirebaseDatabase.getInstance().reference.child("Events").child(eventID)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                textViewTitle.text = snapshot.child("eventTitle").value.toString()
                textViewDate.text = snapshot.child("eventDate").value.toString()
                textViewTime.text = snapshot.child("eventTime").value.toString()
                textViewLocation.text = snapshot.child("eventLocation").value.toString()
                textViewPhoneNum.text = snapshot.child("eventContact").value.toString()
                textViewDesc.text = snapshot.child("eventDescription").value.toString()
                textViewSlotNum.text = snapshot.child("eventRegister").value.toString() + "/" + snapshot.child("eventSlot").value.toString()
                model.setMsgCommunicator(
                    textViewTitle.text.toString(),
                    textViewDate.text.toString(),
                    textViewTime.text.toString(),
                    textViewLocation.text.toString(),
                    textViewPhoneNum.text.toString(),
                    textViewDesc.text.toString()

                )
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        Toast.makeText(context, "You click on item # $eventID", Toast.LENGTH_SHORT).show()
        Log.i("Event ID", "$eventID")



        model.title.observe(viewLifecycleOwner,
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
            { o -> textViewDesc.text = o!!.toString() })

        /*val imageButtonEditText:ImageButton = root.findViewById(R.id.imageButtonEditText)*/




        val auth = Firebase.auth
        val user = auth.currentUser?.uid

        val organiserRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("OrganisedEvents").orderByKey()
        val organiserEventIDList: MutableList<String> = mutableListOf()

        organiserRef.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children)
                {
                    organiserEventIDList.add(item.value.toString())
                }
                if (eventID in organiserEventIDList)
                {
                    if (!isAdded) return;
                    CachePot.getInstance().push(eventID)
                    val manager = childFragmentManager
                    val transaction: FragmentTransaction = manager.beginTransaction()
                    transaction.replace(R.id.fragment, OrganiserEventsFragment())
                    transaction.commit()

                }
                else
                {
                    val volunteerRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("VolunteeredEvents").orderByKey()
                    val volunteerEventIDList: MutableList<String> = mutableListOf()
                    volunteerRef.addValueEventListener(object :ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (item in snapshot.children) {
                                volunteerEventIDList.add(item.value.toString())
                            }

                            if (eventID in volunteerEventIDList)
                            {
                                CachePot.getInstance().push(eventID)
                                if (!isAdded) return;
                                val manager = childFragmentManager
                                val transaction: FragmentTransaction = manager.beginTransaction()
                                transaction.replace(R.id.fragment, VolunteerEventsFragment())
                                transaction.commit()
                            }
                            else
                            {
                                val completedRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("CompletedEvents").orderByKey()
                                val completedEventIDList: MutableList<String> = mutableListOf()
                                volunteerRef.addValueEventListener(object :ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (item in snapshot.children) {
                                            completedEventIDList.add(item.value.toString())
                                        }

                                        if (eventID in completedEventIDList)
                                        {
                                            CachePot.getInstance().push(eventID)
                                            if (!isAdded) return;
                                            val manager = childFragmentManager
                                            val transaction: FragmentTransaction = manager.beginTransaction()
                                            transaction.replace(R.id.fragment, CompletedEventFragment())
                                            transaction.commit()
                                        }
                                        else
                                        {
                                            if (!isAdded) return;
                                            CachePot.getInstance().push(eventID)
                                            val manager = childFragmentManager
                                            val transaction: FragmentTransaction = manager.beginTransaction()
                                            transaction.replace(R.id.fragment, UserEventFragment())
                                            transaction.commit()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





        Log.i("Organiser Event ID List","$organiserEventIDList")


        /*imageButtonEditText.setOnClickListener {
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.navigation_editEvents)
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()

            val editEventsFragment: Fragment? =
                parentFragmentManager.findFragmentById(R.id.navigation_editEvents)
            if (editEventsFragment != null) {
                fragmentTransaction.replace(R.id.nav_host_fragment, EditEventsFragment())
            }
            fragmentTransaction.commit()
        }*/




        return root
    }


    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        inflater.inflate(R.menu.action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = childFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()

        val auth = Firebase.auth
        val user = auth.currentUser?.uid

        val organiserRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("OrganisedEvents").orderByKey()
        val organiserEventIDList: MutableList<String> = mutableListOf()

        organiserRef.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children)
                {
                    organiserEventIDList.add(item.value.toString())
                }
                if (eventID in organiserEventIDList)
                {
                    transaction.add(R.id.fragment, OrganiserEventsFragment())
                    transaction.commit()


                }
                else
                {
                    val volunteerRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("VolunteeredEvents").orderByKey()
                    val volunteerEventIDList: MutableList<String> = mutableListOf()
                    volunteerRef.addValueEventListener(object :ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (item in snapshot.children) {
                                volunteerEventIDList.add(item.value.toString())
                            }

                            if (eventID in volunteerEventIDList)
                            {
                                transaction.add(R.id.fragment, VolunteerEventsFragment())
                                transaction.commit()
                            }
                            else
                            {
                                transaction.add(R.id.fragment, UserEventFragment())
                                transaction.commit()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }*/


}
