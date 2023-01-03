package com.example.clgshare

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.InternalCoroutinesApi


class PostFragment : Fragment()  {

    var Post: Button?=null
    var Subject:TextView?=null
    var Description:TextView?=null
    var PostImage:ImageView?=null
    var Camera:ImageView?=null
    var progressBar: ProgressBar? = null
    var uri: Uri? = null
    var stringuri:String?=null
    var bitmap: Bitmap? = null
    var a = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_post, container, false)

        Post=view.findViewById(R.id.Post)
        Subject=view.findViewById(R.id.Subject)
        Description=view.findViewById(R.id.Description)
        PostImage=view.findViewById(R.id.PostImage)
        Camera=view.findViewById(R.id.Camera)
        progressBar=view.findViewById(R.id.progressBar)

        // Room Database:
        val Database: AppDatabase = Room.databaseBuilder(requireActivity(), AppDatabase::class.java as Class<AppDatabase>, "APP DATA").allowMainThreadQueries().build()
        val appDataDao: AppDataDao = Database.appDataDao()

        // Firebase Database :
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

        // Firebase Storage :
        val storage = FirebaseStorage.getInstance()


        // Post :
        Camera?.setOnClickListener(View.OnClickListener {
            imageChooser()
            a = 1
        })

        // Post :
        Post?.setOnClickListener(View.OnClickListener {
            if (a != 1) {
                Toast.makeText(context, "Please Add Image", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }


            // Alert Box :
            var builder : AlertDialog.Builder = AlertDialog.Builder(getContext())
            builder.setMessage("Share as ?").setCancelable(true)
                .setPositiveButton("Post",
                    DialogInterface.OnClickListener { dialog, which ->
                        progressBar?.setVisibility(View.VISIBLE)


                        val posts: DatabaseReference = firebaseDatabase.getReference("POST")
                        val s:String=firebaseAuth.uid.toString()+appDataDao.getPostDataCount().toString()

                        // Image uploading to Firease Storage :
                        val PostImages = storage.reference.child("Post Image").child(s)

                        // Adding and getting Image by Firebase Storage :
                        PostImages.putFile(uri!!)
                            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {
                                PostImages.downloadUrl.addOnSuccessListener {
                                    val myPostData:MyPostData=MyPostData(s,it.toString(),Subject?.text.toString(),Description?.text.toString())
                                    posts.child(s).setValue(myPostData)
                                    appDataDao.insertPostData(myPostData)

                                    Toast.makeText(context, "Post Added", Toast.LENGTH_SHORT).show()

                                    progressBar?.setVisibility(View.INVISIBLE)
                                    PostImage?.setImageResource(R.drawable.postimage)
                                    Subject?.text=""
                                    Description?.text=""
                                }
                            })

                        dialog.cancel()
                    }).setNegativeButton("Query",
                    DialogInterface.OnClickListener { dialog, which ->
                        progressBar?.setVisibility(View.VISIBLE)

                        val querys: DatabaseReference = firebaseDatabase.getReference("QUERY")
                        val s:String=firebaseAuth.uid.toString()+appDataDao.getQueryDataCount().toString()

                        // Image uploading to Firease Storage :
                        val PostImages = storage.reference.child("Query Image").child(s)

                        // Adding and getting Image by Firebase Storage :
                        PostImages.putFile(uri!!)
                            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {
                                PostImages.downloadUrl.addOnSuccessListener {
                                    val myQueryData:MyQueryData= MyQueryData(s,it.toString(),Subject?.text.toString(),Description?.text.toString())
                                    querys.child(s).setValue(myQueryData)
                                    appDataDao.insertQueryData(myQueryData)

                                    Toast.makeText(context, "Query Added", Toast.LENGTH_SHORT).show()

                                    progressBar?.setVisibility(View.INVISIBLE)
                                    PostImage?.setImageResource(R.drawable.postimage)
                                    Subject?.text=""
                                    Description?.text=""
                                }

                            })

                        dialog.cancel()
                    }).show()
        })
        return view
    }

    // Image Selector:
    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200) {
            uri = data?.data
            try {
                val inputStream = requireActivity().contentResolver.openInputStream(
                    uri!!
                )
                bitmap = BitmapFactory.decodeStream(inputStream)
                PostImage?.setImageBitmap(bitmap)
            } catch (e: Exception) {
            }
        }
    }


}
