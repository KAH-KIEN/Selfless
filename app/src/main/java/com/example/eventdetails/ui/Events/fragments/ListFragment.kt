package com.android.example.eventactivity.fragments
//Credit to kimkevin-CachePot
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
import com.google.firebase.database.*

class ListFragment : Fragment() {

    lateinit var ref: DatabaseReference
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
                //do whatever you need
            }
        })
        return view
    }
}