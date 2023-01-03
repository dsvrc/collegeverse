package com.example.clgshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.util.*

class MyPostFragment : Fragment() {

    var Database:AppDatabase?=null
    lateinit var appDataDao: AppDataDao
    lateinit var MyPostRecyclerView: RecyclerView
    lateinit var LikeRecyclerViewAdapter: HomeRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypost, container, false)
        MyPostRecyclerView=view.findViewById(R.id.MyPostRecyclerView)

//         Room Databse Data :
        Database = Room.databaseBuilder(view.context, AppDatabase::class.java as Class<AppDatabase>, "APP DATA").allowMainThreadQueries().build()
        appDataDao= Database!! .appDataDao()

        var pdata : ArrayList<Data>?= ArrayList()

        var PostData=appDataDao.getAllPostData()
        for(like in PostData)
        {
            val data=Data()
            data.uid=like.uid
            data.image= like.image
            data.subject= like.subject
            data.description= like.description
            pdata?.add(data)
        }

        LikeRecyclerViewAdapter=HomeRecyclerViewAdapter(pdata!!)
        MyPostRecyclerView.adapter=LikeRecyclerViewAdapter

        return view
    }
}
