package com.example.aiedudemo.main

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R

class ImportModel: AppCompatActivity() {
    var openFile:Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.importmodel)

        openFile = findViewById(R.id.openFile)

        openFile?.setOnClickListener {
            openFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == RESULT_OK){
            val selectedFile = data?.data
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }
}