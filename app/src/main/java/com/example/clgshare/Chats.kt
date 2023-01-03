package com.example.clgshare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.util.*

class Chats : AppCompatActivity() {

    var Database:AppDatabase?=null
    lateinit var appDataDao: AppDataDao
    lateinit var chatRecyclerView: RecyclerView
    lateinit var chatRecyclerViewAdapter: MyConnectionRecyclerViewAdapter
    private lateinit var MyConnectionData: ArrayList<MyConnectionData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        chatRecyclerView=findViewById(R.id.ChatR)

//         Room Databse Data :
        Database = Room.databaseBuilder(this, AppDatabase::class.java as Class<AppDatabase>, "APP DATA").allowMainThreadQueries().build()
        appDataDao= Database!! .appDataDao()

        MyConnectionData= appDataDao.getAllConnectionData() as ArrayList<MyConnectionData>

//        var LikeData=appDataDao.getAllFavouriteData()
//        for(like in LikeData)
//        {
//            val data=Data()
//            data.uid=like.uid
//            data.image= like.image
//            data.subject= like.subject
//            data.description= like.description
//            fdata?.add(data)
//        }

        chatRecyclerViewAdapter=MyConnectionRecyclerViewAdapter(MyConnectionData)
        chatRecyclerView.adapter=chatRecyclerViewAdapter
    }
}