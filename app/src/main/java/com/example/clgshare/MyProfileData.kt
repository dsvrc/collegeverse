package com.example.clgshare

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MyProfileData {
    @PrimaryKey
    lateinit var uid: String

    @ColumnInfo(name="Image")
    var image: String?=null

    @ColumnInfo(name = "Name")
    var name: String? = null

    @ColumnInfo(name = "Email")
    var email: String? = null

    @ColumnInfo(name = "Phone")
    var phone: String? = null

    @ColumnInfo(name = "About")
    var about: String? = null

    constructor() {}

    constructor(uid: String, image: String?, name: String?, email: String?, phone: String?, about: String?) {
        this.uid = uid
        this.image = image
        this.name = name
        this.email = email
        this.phone = phone
        this.about = about
    }

    constructor( name: String?,email: String?) {
        this.name = name
        this.email = email

    }
}
