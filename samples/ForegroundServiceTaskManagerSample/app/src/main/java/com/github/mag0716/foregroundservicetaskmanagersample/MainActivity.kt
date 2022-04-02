package com.github.mag0716.foregroundservicetaskmanagersample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_service1_button).setOnClickListener {
            val intent = Intent(this, LoggingForegroundService1::class.java)
            startService(intent)
        }

        findViewById<Button>(R.id.stop_service1_button).setOnClickListener {
            val intent = Intent(this, LoggingForegroundService1::class.java)
            stopService(intent)
        }

        findViewById<Button>(R.id.start_service2_button).setOnClickListener {
            val intent = Intent(this, LoggingForegroundService2::class.java)
            startService(intent)
        }

        findViewById<Button>(R.id.stop_service2_button).setOnClickListener {
            val intent = Intent(this, LoggingForegroundService2::class.java)
            stopService(intent)
        }
    }
}