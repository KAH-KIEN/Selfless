package com.example.eventdetails.ui.MyEvents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.eventactivity.fragments.adapters.RecyclerAdapter
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventRead
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class OrganiserEventListFragment : Fragment() {

    lateinit var ref: DatabaseReference
    //lateinit var eventList: MutableList<Event>
    lateinit var eventList : ArrayList<EventRead>

    var auth = Firebase.auth
    var userID = auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = LayoutInflater.from(activity).inflate(R.layout.fragment_list, container, false)

        //eventList = mutableListOf()
        val recyclerView: RecyclerView = view.findViewById(R.id.recycleList)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val eventIDList: MutableList<String> = mutableListOf()

        eventList = arrayListOf<EventRead>()

        val auth = Firebase.auth
        val user = auth.currentUser?.uid
        val myRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("OrganisedEvents").orderByKey()
        myRef.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children)
                {
                    eventIDList.add(item.value.toString())

                }


                ref = FirebaseDatabase.getInstance().getReference("Events")

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //check if there is any data exit in the database
                        if(snapshot!!.exists()){
                            eventList.clear()
                            for(e in snapshot.children){
                                val events = e.getValue(EventRead::class.java)
                                if (events?.eventID in eventIDList){
                                    eventList.add(events!!)
                                }

                            }

                            val adapter = context?.let { RecyclerAdapter(it, eventList) }
                            recyclerView.adapter = adapter
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        Log.i("Organiser Event Main","$eventIDList")
        /*ref = FirebaseDatabase.getInstance().getReference("Events")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //check if there is any data exit in the database
                if(snapshot!!.exists()){
                    eventList.clear()
                    for(e in snapshot.children){
                        val events = e.getValue(EventRead::class.java)
                        eventList.add(events!!)
                    }

                    val adapter = context?.let { RecyclerAdapter(it, eventList) }
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })*/


        return view
    }




}