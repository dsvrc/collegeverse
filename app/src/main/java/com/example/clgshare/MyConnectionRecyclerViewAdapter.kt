package com.example.clgshare

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.util.*


class MyConnectionRecyclerViewAdapter(data: ArrayList<MyConnectionData>) : RecyclerView.Adapter<MyConnectionRecyclerViewAdapter.ViewHolder>() {

    var Database: AppDatabase? = null
    var builder: AlertDialog.Builder? = null
    var Data: ArrayList<MyConnectionData>?=data


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.connection_layout, parent, false)
        Database = Room.databaseBuilder(view.context, AppDatabase::class.java as Class<AppDatabase>, "APP DATA").allowMainThreadQueries().build()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Firebase :
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val users = firebaseDatabase.getReference("USERS")

        // Firebase Storage :
        val storage = FirebaseStorage.getInstance()
        val HouseImages = storage.reference.child("Profile Image").child(Data!![position].uid)

        // Setting Text and Image:
        users.child(Data!![position].uid).get().addOnSuccessListener {
            holder.name.text = it.getValue().toString() }
        try {
            val file = File.createTempFile("tempfile", ".jpg")
            HouseImages.getFile(file)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    holder.pic.setImageBitmap(bitmap)
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return Data!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView
        var name: TextView

        init {
            pic = itemView.findViewById(R.id.UserImage)
            name = itemView.findViewById(R.id.UserName)
        }
    }

}