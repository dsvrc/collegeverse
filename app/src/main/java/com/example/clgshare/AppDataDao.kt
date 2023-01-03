package com.example.clgshare

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDataDao {

    // Insertion :

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPostData(myPostData: MyPostData?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQueryData(myQueryData: MyQueryData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProfileData(myProfileData: MyProfileData?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteData(myFavouriteData: MyFavouriteData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConnectionData(myConnectionData: MyConnectionData?)

    // Deletion By ID :

    @Query("DELETE FROM MyQueryData WHERE uid= :qid")
    fun deleteQueryDatabyID(qid:String?)

    @Query("DELETE FROM MyFavouriteData WHERE uid= :fid")
    fun deleteFavouriteDatabyID(fid: String?)

    @Query("DELETE FROM MyPostData WHERE uid= :pid")
    fun deletePostDatabyID(pid: String?)

    @Query("DELETE FROM MyConnectionData WHERE uid= :cid")
    fun deleteConnectionDatabyID(cid: String?)

    @Query("SELECT * FROM MyQueryData ORDER BY uid ASC")
    fun getAllQueryData(): List<MyQueryData>

    @Query("SELECT * FROM MyPostData")
    fun getAllPostData(): List<MyPostData>

    @Query("SELECT * FROM MyProfileData")
    fun getAllProfileData(): List<MyProfileData>

    @Query("SELECT * FROM MyFavouriteData")
    fun getAllFavouriteData(): List<MyFavouriteData>

    @Query("SELECT * FROM MyConnectionData")
    fun getAllConnectionData(): List<MyConnectionData>

    @Query("DELETE FROM MyProfileData")
    fun deleteAllProfileData()

    @Query("SELECT COUNT() FROM MyPostData")
    fun getPostDataCount(): Int

    @Query("SELECT COUNT() FROM MyQueryData")
    fun getQueryDataCount(): Int

    @Query("SELECT COUNT() FROM MyProfileData")
    fun getProfileDataCount(): Int

    @Query("SELECT EXISTS(SELECT * FROM MyFavouriteData WHERE uid= :fid)")
    fun is_hexist(fid: String?): Boolean?

    @Query("SELECT EXISTS(SELECT * FROM MyConnectionData WHERE uid= :cid)")
    fun is_pexist(cid: String?): Boolean?


}