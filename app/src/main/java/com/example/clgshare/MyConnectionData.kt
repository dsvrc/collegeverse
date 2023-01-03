package com.example.clgshare

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MyConnectionData {
    @PrimaryKey
    lateinit var uid: String

    constructor(uid: String) {
        this.uid = uid
    }

    constructor() {}
}
