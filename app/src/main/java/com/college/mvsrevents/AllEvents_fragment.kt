package com.college.mvsrevents

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [AllEvents_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
var eventlist: MutableList<EventsHelperClass> = ArrayList()
//var eventnamelist:MutableList<String> = ArrayList()
var eventposterlist:MutableList<Int> = ArrayList()
class AllEvents_fragment : Fragment() {
    lateinit var recyclerAllevents: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter:AlleventsRecyclerAdapter
    val Reference=Firebase.database.getReference("events")
    /*val event1:EventsHelperClass =
   EventsHelperClass("Fusion2021","12345","28-08-0102")
    val event2:EventsHelperClass =
   EventsHelperClass("Fusion2021","12345","28-08-0102")
    val event3:EventsHelperClass =
   EventsHelperClass("Fusion2021","12345","28-08-0102"," ")
    val event4:EventsHelperClass =
   EventsHelperClass("Fusion2021","12345","28-08-0102"," ")
    val eventsList : ArrayList<EventsHelperClass> = arrayListOf <EventsHelperClass>
   (event1,event2,event3,event4)*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_events_fragment,
            container, false)
        recyclerAllevents = view.findViewById(R.id.recyclerView_Allevents)
        layoutManager=LinearLayoutManager(context)
        //println(eventlist)
        //println(eventlist+"up")
        Reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children=snapshot!!.children
                children.forEach{
                    var temp=
                        EventsHelperClass(it.child("eventname").getValue().toString(),it.child("eventdate")
                            .getValue().toString(),it.child("eventcoordinator").getValue().toString(),it.child(
                            "posters").getValue().toString())
                    //val verelist: MutableList<String> = ArrayList()
                    //verelist.add(it.child("eventname").getValue().toString())
                    //verelist.add(it.child("posters").getValue().toString())
                    eventlist.add(temp)
                    println(eventlist)
                }
                eventposterlist.add(R.drawable.all_events)
                eventposterlist.add(R.drawable.mvsr_logo)
                //val eventlist : ArrayList<MutableList<Int>> = arrayListOf(eventnamelist, eventposterlist)
                recyclerAdapter= AlleventsRecyclerAdapter(activity as Context,
                    eventlist)
                recyclerAllevents.adapter=recyclerAdapter
                recyclerAllevents.layoutManager=layoutManager
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //print(eventlist+"down")
        return view
    }
}
