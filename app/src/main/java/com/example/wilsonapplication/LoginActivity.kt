package com.example.wilsonapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        use_phone_button_register.setOnClickListener {
            Log.d("LoginActivity", "Try to show login Messages")

            //Launch the login activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        next_button_register.setOnClickListener {
            //Launch the map activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
