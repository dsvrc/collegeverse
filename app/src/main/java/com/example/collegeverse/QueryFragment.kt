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

class QueryFragment : Fragment() {

    lateinit var queryRecyclerView: RecyclerView
    lateinit var queryRecyclerViewAdapter: QueryRecyclerViewAdapter
    private lateinit var MyPostDataList: ArrayList<Data>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_query,container , false)
        queryRecyclerView=view.findViewById(R.id.QueryRecyclerView)

//         Firebase Databse Data :
        MyPostDataList= ArrayList<Data>()
//        val data=MyPostData("1","","hi","Sai")
        val reference= Firebase.database.getReference("QUERY")
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
        queryRecyclerViewAdapter=QueryRecyclerViewAdapter(MyPostDataList)
        queryRecyclerView.adapter=queryRecyclerViewAdapter

        return view    }
}
