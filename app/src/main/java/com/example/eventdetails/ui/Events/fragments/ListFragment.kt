package com.android.example.eventactivity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.eventactivity.fragments.adapters.RecyclerAdapter
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventRead
import com.google.firebase.database.*

class ListFragment : Fragment() {

//    private var titleList = mutableListOf<String>()
//    private var dateList = mutableListOf<String>()
//    private var durationList = mutableListOf<String>()
//    private var locationList = mutableListOf<String>()
//    private var contactList = mutableListOf<String>()

    lateinit var ref: DatabaseReference
    //lateinit var eventList: MutableList<Event>
    lateinit var eventList : ArrayList<EventRead>


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

        eventList = arrayListOf<EventRead>()
        ref = FirebaseDatabase.getInstance().getReference("Events")

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

        })


        return view
    }


//    private fun addToList(eventTitle: String, eventDate: String, eventDuration: String, eventLocation: String, eventContact: String){
//        titleList.add(eventTitle)
//        dateList.add(eventDate)
//        durationList.add(eventDuration)
//        locationList.add(eventLocation)
//        contactList.add(eventContact)
//    }
//
//    private fun postToList(){
//        for (i in 1..5)
//            addToList("Title $i", "Date $i", "Duration $i", "Location $i", "Contact $i")
//    }


//        postToList()
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.recycleList);
//        recyclerView.layoutManager = LinearLayoutManager(this.context)
//        recyclerView.adapter = RecyclerAdapter(titleList, dateList, durationList, locationList, contactList)

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL
//                recyclerview.setLayoutManager(layoutManager);
//        recyclerview.setHasFixedSize(true);
//        recyclerviewAdapter adapter = new recyclerviewAdapterAdapter(listdata,getActivity());
//        recyclerview.setAdapter(adapter);

//        val recyclerView: RecyclerView = view.findViewById(R.id.recycleList);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        //recyclerView.setAdapter(new RandomNumListAdapter(1234));
//        recyclerView.adapter = RecyclerAdapter(titleList, dateList, durationList, locationList, contactList)


}