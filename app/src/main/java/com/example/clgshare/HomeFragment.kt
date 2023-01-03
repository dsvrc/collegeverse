package com.example.clgshare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class HomeFragment : Fragment() {

    lateinit var homeRecyclerView: RecyclerView
    lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private lateinit var MyPostDataList:ArrayList<Data>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeRecyclerView=view.findViewById(R.id.HomeRecyclerView)

//         Firebase Databse Data :
        MyPostDataList= ArrayList<Data>()

        val reference= Firebase.database.getReference("POST")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for (usersnapShot in snapshot.children){
                        val data=Data()
                        data.uid=usersnapShot.child("uid").getValue().toString()
                        data.image=usersnapShot.child("image").getValue().toString()
                        data.subject=usersnapShot.child("subject").getValue().toString()
                        data.description=usersnapShot.child("description").getValue().toString()
                        MyPostDataList.add(data)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error",error.toString())
            }
        })

        homeRecyclerViewAdapter=HomeRecyclerViewAdapter(MyPostDataList)
        homeRecyclerView.adapter=homeRecyclerViewAdapter

        return view
    }
}
