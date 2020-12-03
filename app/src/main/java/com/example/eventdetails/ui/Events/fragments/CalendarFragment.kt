package com.android.example.eventactivity.fragments
//Credit to SundeepK-CompactCalendarView
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventDateRead
import com.example.eventdetails.ui.Firebase.EventRead
import com.github.kimkevin.cachepot.CachePot
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

//Credits to kimkevin : CachePot

class CalendarFragment : Fragment() {
    var compactCalendar: CompactCalendarView? = null
    private val dateFormatMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
    private val eventDateList = arrayListOf<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_calendar, container, false)
        val textDate: TextView = view.findViewById(R.id.textDate)
        val textCalendarViewTitle: TextView = view.findViewById(R.id.textCalendarViewTitle)
        val textCalendarViewDate: TextView = view.findViewById(R.id.textCalendarViewDate)
        val textCalendarViewTime: TextView = view.findViewById(R.id.textCalendarViewTime)
        val textCalendarViewLocation: TextView = view.findViewById(R.id.textCalendarViewLocation)
        val textCalendarViewPhoneNum: TextView = view.findViewById(R.id.textCalendarViewPhoneNum)
        val buttonRegister: Button = view.findViewById(R.id.buttonRegister)
        textDate.setText(dateFormatMonth)

        compactCalendar = view.findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView
        compactCalendar!!.setUseThreeLetterAbbreviation(true)

        //Get all eventDate from firebase and store into an array
        //eventDateList = arrayListOf<String?>()
        val ref = FirebaseDatabase.getInstance().reference.child("Events")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){
                    eventDateList.clear()
                    for(date in snapshot.children){
                        val eventDate = date.getValue(EventDateRead::class.java)
                        eventDateList.add(eventDate?.getEventDate())
                    }}
                //  Convert into millisecond
                var eventDateListMillis = mutableListOf<Long>()
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                try {
                    for (item in eventDateList){
                        val mDate = sdf.parse(item.toString())
                        val timeInMilliseconds = mDate.time
                        eventDateListMillis.add(timeInMilliseconds)
                    }

                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                //Add marker in calendar
                for (item in eventDateListMillis){
                    compactCalendar!!.addEvent(Event(Color.RED, item))
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        //Calendar onDayClick
        compactCalendar!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val context = getActivity()?.getApplicationContext()

                val parser = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                //val currentDate = formatter.format(Date())
                var currentDate:String = ""
                val output = formatter.format(parser.parse(dateClicked.toString()))
                var valid: Boolean = FALSE

                //check whether the date exist in firebase
                for (item in eventDateList){
                    if (item == output){
                        valid = TRUE
                        currentDate = item.toString()
                        break
                    }
                }
                if (valid == TRUE) {
//                    Toast.makeText(
//                        context,
//                        "Today is a good day, $currentDate",
//                        Toast.LENGTH_SHORT
//                    ).show()

                    //Show info
                    val mRefID = FirebaseDatabase.getInstance().reference
                    mRefID.child("Events").orderByChild("eventDate").equalTo(currentDate)
                        .addListenerForSingleValueEvent(object: ValueEventListener {

                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            dataSnapshot.children.forEach {
                                val keyID: String = it.key.toString()
                                //Toast.makeText(context,  "RespectiveID, $keyID", Toast.LENGTH_SHORT).show()

                                val mRefInfo = FirebaseDatabase.getInstance().reference.child("Events").child(keyID)
                                mRefInfo.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        textCalendarViewTitle.text = snapshot.child("eventTitle").value.toString()
                                        textCalendarViewDate.text = snapshot.child("eventDate").value.toString()
                                        textCalendarViewTime.text = snapshot.child("eventTime").value.toString()
                                        textCalendarViewLocation.text = snapshot.child("eventLocation").value.toString()
                                        textCalendarViewPhoneNum.text = snapshot.child("eventContact").value.toString()

                                        buttonRegister.setOnClickListener{
                                            CachePot.getInstance().push(keyID);
                                            requireView().findNavController().navigate(R.id.navigation_eventDetails)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        //do whatever you need
                                    }
                                })
                            }
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            //do whatever you need
                        }
                    })

                } else {
                    Toast.makeText(
                        context,
                        "No Events Planned for that day",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                val inputFormat: DateFormat = SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy")
                val d: Date = inputFormat.parse(firstDayOfNewMonth.toString())
                val outputFormat: DateFormat = SimpleDateFormat("MMMM yyyy")
                Log.d("TAG", "Month was scrolled to: " + outputFormat.format(d))
                textDate.setText(outputFormat.format(d))
            }
        })

        return view
    }



}


//        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
//        val myDate: TextView = view.findViewById(R.id.myDate)



//        calendarView.setOnDateChangeListener {calenderView, i, i2, i3 ->
//            val pickData: String = "$i3/$i2/$i"
//            myDate.setText(pickData)
//        }