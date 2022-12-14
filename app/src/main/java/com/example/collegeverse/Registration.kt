package com.example.clgshare

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.volley.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


class Registration : AppCompatActivity() {

    val url =null
    var f = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting layout :
        setContentView(R.layout.activity_registration)

        val tablayout=findViewById<TabLayout>(R.id.TabLayout)
        val viewPager=findViewById<ViewPager>(R.id.ViewPager)

        // If Current User :
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // setting Fragments by Adapters:
        val registrationAdapter =
            RegistrationAdapter(supportFragmentManager, this, tablayout.getTabCount())
        viewPager.adapter = registrationAdapter

        // setting Fragments when Page changed:
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))

        // setting Fragments when Tab item clicked:
        tablayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        //Animating ViewPager:
        viewPager.setTranslationY(1000f)
        viewPager.alpha = f
        viewPager.animate().translationYBy(-1000f).alpha(1f).setDuration(750).start()


        // Animating TabLayout:
        tablayout.setAlpha(f)
        tablayout.animate().alpha(1f).setDuration(1000).start()

        // Images:
        val twitter = findViewById<ImageView>(R.id.twitter)
        val google = findViewById<ImageView>(R.id.google)
        val facebook = findViewById<ImageView>(R.id.facebook)

        // Animating Images:
        twitter.setTranslationX(-500f)
        facebook.setTranslationX(500f)
        google.setTranslationY(500f)
        twitter.animate().translationXBy(500f).setDuration(700).setStartDelay(500).start()
        facebook.animate().translationXBy(-500f).setDuration(700).setStartDelay(500).start()
        google.animate().translationYBy(-500f).setDuration(700).setStartDelay(350)
    }

    // Login Activity:
    fun Login(view: View?) {

        // Intialising Variables:
        val login_email = findViewById(R.id.login__email) as EditText
        val login_password = findViewById(R.id.login_password) as EditText
        val Log = findViewById(R.id.LprogressBar) as ProgressBar
        val email: String = login_email.getText().toString()
        val password: String = login_password.getText().toString()

        // Checking conditions:
        if (TextUtils.isEmpty(email)) {
            login_email.setError("Email is required")
            return
        }
        if (TextUtils.isEmpty(password)) {
            login_password.setError("Password is required")
            return
        }
        if (password.length < 6) {
            login_password.setError("Password must be >= 6 characters")
            return
        }
        Log.setVisibility(View.VISIBLE)

//        // Firease Sigin:
        var firebaseAuth =FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
            OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    Log.visibility = View.INVISIBLE
                    Toast.makeText(this@Registration, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this@Registration.applicationContext, MainActivity::class.java))
                } else {
                    Log.visibility = View.INVISIBLE
                    Toast.makeText(this@Registration, "Error!", Toast.LENGTH_SHORT).show()
                }
            })

//        // Volley ==> Signin :
//        val stringRequest: StringRequest = object : StringRequest(
//            Request.Method.POST, "http://localhost:3000/users/login",
//            Response.Listener<String> { response ->
//                if (response == "success") {
//                    val intent = Intent(this@Registration, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                } else if (response == "failure") {
//                    Toast.makeText(this@Registration, "Invalid Credentials", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            },
//            Response.ErrorListener { error ->
//                Toast.makeText(
//                    this@Registration,
//                    error.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }) {
//            @get:Throws(AuthFailureError::class)
//            protected val params: Any
//                protected get() {
//                    val data: HashMap<Any, Any> = HashMap<Any, Any>()
//                    data.put("email", email)
//                    data.put("password", password)
//                    return data
//                }
//        }
//        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
//        requestQueue.add(stringRequest)
    }

    // Signup Activity:
    fun Signup(view: View?) {

        // Intialising Variables:
        val signup_username = findViewById(R.id.signup_username) as EditText
        val signup_email = findViewById(R.id.signup_email) as EditText
        val signup_password = findViewById(R.id.signup_password) as EditText
        val signup_create_password = findViewById(R.id.signup_create_password) as EditText
        val Sign = findViewById(R.id.SprogressBar) as ProgressBar
        val Username: String = signup_username.getText().toString()
        val Email: String = signup_email.getText().toString()
        val Password: String = signup_password.getText().toString()
        val create_password: String = signup_create_password.getText().toString()

        // Checking conditions:
        if (TextUtils.isEmpty(Username)) {
            signup_username.setError("Username is required")
            return
        }
        if (TextUtils.isEmpty(Email)) {
            signup_email.setError("Email is required")
            return
        }
        if (TextUtils.isEmpty(Password)) {
            signup_password.setError("Password is required")
            return
        }
        if (Password.length < 6) {
            signup_password.setError("Password must be >= 6 characters")
            return
        }
        if (Password != create_password) {
            signup_create_password.setError("Recheck password")
            return
        }
        Sign.setVisibility(View.VISIBLE)

        // Firebase Signup :
        var firebaseAuth =FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(
            OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {

                    // Firebase Database:
                    val firebaseDatabase = FirebaseDatabase.getInstance()
                    val users = firebaseDatabase.getReference("USERS")
                    users.child(firebaseAuth.uid.toString()).setValue(Username)

                    Sign.visibility = View.INVISIBLE
                    Toast.makeText(this@Registration, "Signup Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Registration, MainActivity::class.java))
                } else {
                    Sign.visibility = View.INVISIBLE
                    Toast.makeText(this@Registration, "Error!", Toast.LENGTH_SHORT).show()
                }
            })

        // Volley ==> Signup :
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST, "http://localhost:3000/users",
//            Response.Listener<String> { response ->
//                if (response == "success") {
//                    Toast.makeText(this@Registration,"Login Successfull",Toast.LENGTH_SHORT)
//                    startActivity(
//                        Intent(
//                            this@Registration.getApplicationContext(),
//                            Registration::class.java
//                        )
//                    )
//                    startActivity(intent)
//                    finish()
//                } else if (response == "failure") {
//                    Toast.makeText(this@Registration, "Invalid Credentials", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            },
//            Response.ErrorListener { error ->
//                Toast.makeText(
//                    this@Registration,
//                    error.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }) {
//            @get:Throws(AuthFailureError::class)
//            protected val params: Any
//                protected get() {
//                    val data: HashMap<Any, Any> = HashMap<Any, Any>()
//                    data.put("name",signup_username)
//                    data.put("email", signup_email)
//                    data.put("password", signup_password)
//                    return data
//                }
//        }
//        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
//        requestQueue.add(stringRequest)
    }
}