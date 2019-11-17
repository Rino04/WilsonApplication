package com.example.wilsonapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email_button_register.setOnClickListener {
            Log.d("registerActivity", "Try to show login Messages")

            //Launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        next_button_register.setOnClickListener {
            //Launch the map activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}
