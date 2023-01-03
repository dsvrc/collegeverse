package com.example.clgshare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class LikeFragment : Fragment() {

    var Database:AppDatabase?=null
    lateinit var appDataDao: AppDataDao
    lateinit var LikeRecyclerView: RecyclerView
    lateinit var LikeRecyclerViewAdapter: HomeRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_like, container, false)
        LikeRecyclerView=view.findViewById(R.id.LikeRecyclerView)

//         Room Databse Data :
        Database = Room.databaseBuilder(view.context, AppDatabase::class.java as Class<AppDatabase>, "APP DATA").allowMainThreadQueries().build()
        appDataDao= Database!! .appDataDao()

        var fdata :ArrayList<Data>?= ArrayList()

        var LikeData=appDataDao.getAllFavouriteData()
        for(like in LikeData)
        {
            val data=Data()
            data.uid=like.uid
            data.image= like.image
            data.subject= like.subject
            data.description= like.description
            fdata?.add(data)
        }

        LikeRecyclerViewAdapter=HomeRecyclerViewAdapter(fdata!!)
        LikeRecyclerView.adapter=LikeRecyclerViewAdapter

        return view
    }
}
