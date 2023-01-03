package com.example.clgshare

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity
class MyPostData {
    @PrimaryKey
    lateinit var uid : String
    @ColumnInfo(name="Image")
    var image : String?
    @ColumnInfo(name="Subject")
    var subject:String?
    @ColumnInfo(name="Description")
    var description:String?

    constructor(uid: String, image: String?, subject: String?, description: String?) {
        this.uid = uid
        this.image = image
        this.subject = subject
        this.description = description
    }

}