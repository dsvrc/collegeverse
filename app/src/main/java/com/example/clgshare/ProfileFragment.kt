package com.example.clgshare

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.InternalCoroutinesApi


class ProfileFragment : Fragment() {

    var ProfilePic: ImageView? = null
    var aa = 0
    var uri: Uri? = null
    var bitmap: Bitmap? = null
    var N = 0
    var E = 0
    var P = 0
    var A = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view:View =inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialising Variables:
        ProfilePic = view.findViewById<ImageView>(R.id.ProfilePersonImage)
        var Camera = view.findViewById<ImageView>(R.id.ProfileCamera)
        var Logout = view.findViewById<Button>(R.id.profilelogout)
        var EditName = view.findViewById<ImageView>(R.id.EditName)
        var EditEmail = view.findViewById<ImageView>(R.id.EditEmail)
        var EditPhone = view.findViewById<ImageView>(R.id.EditPhone)
        var EditAbout = view.findViewById<ImageView>(R.id.EditAbout)
        var EditN = view.findViewById<EditText>(R.id.EditProfileName)
        var EditP = view.findViewById<EditText>(R.id.EditProfilePhone)
        var EditE = view.findViewById<EditText>(R.id.EditProfileEmail)
        var EditA = view.findViewById<EditText>(R.id.EditProfileAbout)
        var Name = view.findViewById<TextView>(R.id.ProfileName)
        var Email = view.findViewById<TextView>(R.id.ProfileEmail)
        var Phone = view.findViewById<TextView>(R.id.ProfilePhone)
        var About = view.findViewById<TextView>(R.id.ProfileAbout)
        var Submit = view.findViewById<Button>(R.id.submit)
        var progressBar = view.findViewById<ProgressBar>(R.id.PprogressBar)
        val pic:ImageView=view.findViewById(R.id.ProfilePersonImage)


        // Room Database:
        val Database: AppDatabase = Room.databaseBuilder(requireActivity(), AppDatabase::class.java as Class<AppDatabase>, "APP DATA").allowMainThreadQueries().build()
        val appDataDao: AppDataDao = Database.appDataDao()

        // Firebase Database :
        var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

        // Firebase Storege :
        val storage = FirebaseStorage.getInstance()

        // Intial Profile:
        var profileDataList=appDataDao.getAllProfileData()
            if(!profileDataList.isEmpty())
            {
                Glide.with(this).load(profileDataList[profileDataList.size-1].image).centerCrop().placeholder(R.drawable.postimage).into(pic)
                Name.setText("Name: " + profileDataList[profileDataList.size-1].name)
                Email.setText("Email: " + profileDataList[profileDataList.size-1].email)
                Phone.setText("Phone: " + profileDataList[profileDataList.size-1].phone)
                About.setText("About: " + profileDataList[profileDataList.size-1].about)
            }

        // Image Selection:
        Camera.setOnClickListener {
            aa = 1
            Submit.visibility = View.VISIBLE
            imageChooser()
        }

        // Editing Profile :
        EditName.setOnClickListener {
            N = 1
            Name.visibility = View.INVISIBLE
            EditN.visibility = View.VISIBLE
            Submit.visibility = View.VISIBLE
        }
        EditEmail.setOnClickListener {
            E = 1
            Email.visibility = View.INVISIBLE
            EditE.visibility = View.VISIBLE
            Submit.visibility = View.VISIBLE
        }
        EditPhone.setOnClickListener {
            P = 1
            Phone.visibility = View.INVISIBLE
            EditP.visibility = View.VISIBLE
            Submit.visibility = View.VISIBLE
        }
        EditAbout.setOnClickListener {
            A = 1
            About.visibility = View.INVISIBLE
            EditA.visibility = View.VISIBLE
            Submit.visibility = View.VISIBLE
        }

        Submit.setOnClickListener(object : View.OnClickListener {
            var sName: String? = null
            var sEmail: String? = null
            var sPhone: String? = null
            var sAbout: String? = null
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onClick(v: View) {
                if (aa != 1) {
                    Toast.makeText(context, "Add Profile Image", Toast.LENGTH_SHORT).show()
                    return
                }
                progressBar.visibility = View.VISIBLE
                if (N == 1) {
                    EditN.visibility = View.INVISIBLE
                    Name.text = "Name: " + EditN.text.toString()
                    sName = EditN.text.toString()
                    Name.visibility = View.VISIBLE
                }
                if (E == 1) {
                    EditE.visibility = View.INVISIBLE
                    Email.text = "Email: " + EditE.text.toString()
                    sEmail = EditE.text.toString()
                    Email.visibility = View.VISIBLE
                }
                if (P == 1) {
                    EditP.visibility = View.INVISIBLE
                    Phone.text = "Phone: " + EditP.text.toString()
                    sPhone = EditP.text.toString()
                    Phone.visibility = View.VISIBLE
                }
                if (A == 1) {
                    EditA.visibility = View.INVISIBLE
                    About.text = "About: " + EditA.text.toString()
                    sAbout = EditA.text.toString()
                    About.visibility = View.VISIBLE
                }

                Submit.visibility = View.INVISIBLE

                // Uploading Photo to Firebase Storage :
                val ProfileImage = storage.reference.child("Profile Image").child(firebaseAuth.uid.toString())
                if (aa == 1) {
                    ProfileImage.putFile(uri!!).addOnSuccessListener {

                        progressBar.visibility = View.INVISIBLE
                        ProfileImage.getDownloadUrl().addOnSuccessListener(OnSuccessListener<Uri?> {
                            val myProfileData =MyProfileData(firebaseAuth.uid.toString(),it.toString(),Name.text.toString().subSequence(6,Name.text.toString().length).toString(), Email.text.toString().subSequence(7,Email.text.toString().length).toString(),Phone.text.toString().subSequence(7,Phone.text.toString().length).toString(), About.text.toString().subSequence(7,About.text.toString().length).toString())
                            appDataDao.deleteAllProfileData()
                            appDataDao.insertProfileData(myProfileData)
                        })
                        Toast.makeText(context, "Profile Added Successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(context, "Profile Not Added", Toast.LENGTH_SHORT).show()
                }
            }
        })


        // Logout:
        Logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(
                Intent(this@ProfileFragment.activity?.applicationContext, Registration::class.java)
            )
        }
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
            if (data != null) {
                uri = data.data
            }
            try {
                val inputStream = requireActivity().contentResolver.openInputStream(uri!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                ProfilePic?.setImageBitmap(bitmap)
            } catch (e: Exception) {
            }
        }
    }
}

