package com.github.mag0716.photopickersample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var pickImageLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            Log.d("xxx", "pick image : ${data?.data}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.pick_image_button).setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        pickImageLauncher.launch(
            Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                //putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 1)
            }
        )
    }
}