package com.example.clgshare

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Logo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Full Screen:
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Setting Layout:
        setContentView(R.layout.activity_logo)

        // Setting Image:
        val Logo = findViewById<View>(R.id.Logo) as ImageView

        // Getting address of other Activity:
        val intent = Intent(this, Registration::class.java)

        // Timer:
        object : CountDownTimer(1500, 1500) {
            override fun onTick(millisUntilFinished: Long) {
                // Animation of Logo:
                Logo.animate().alpha(1f).setDuration(1250).scaleX(1.3f).scaleY(1.3f) }

            override fun onFinish() {
                // Starting new Activity:
                startActivity(intent)
            }
        }.start()
    }
}