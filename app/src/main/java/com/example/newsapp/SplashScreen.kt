package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.newsapp.ui.HomeActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreen,HomeActivity::class.java)
            startActivity(intent)
            finish()
        },2000)


    }
}