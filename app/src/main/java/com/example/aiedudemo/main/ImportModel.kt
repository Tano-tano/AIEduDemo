package com.example.aiedudemo.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.divyanshu.draw.widget.DrawView
import com.example.aiedudemo.R
import java.io.InputStream

class ImportModel: AppCompatActivity() {
    var openFile:Button? = null
    private var drawView: DrawView? = null
    private var resetButton: Button? = null
    private var inferButton: Button? = null
    private var backButton: Button? = null
    private var resultnum: TextView? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.importmodel)

        openFile = findViewById(R.id.openFile)
        drawView = findViewById(R.id.draw_view)
        drawView?.setStrokeWidth(70.0f)
        drawView?.setColor(Color.WHITE)
        drawView?.setBackgroundColor(Color.BLACK)
        resetButton = findViewById(R.id.resetBtn)
        inferButton = findViewById(R.id.inferBtn)
        backButton = findViewById(R.id.backBtn)
        resultnum = findViewById(R.id.resultNum)

        //自作モデルをインポートする
        openFile?.setOnClickListener {
            selectFile()
        }

        //描画処理
        drawView?.setOnTouchListener{ _, event ->
            drawView?.onTouchEvent(event)
            true
        }

    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("registerForActivityResult(result)", result.toString())

        if (result.resultCode != RESULT_OK) {
            return@registerForActivityResult
        } else {
            try {
                result.data?.data?.also { uri : Uri ->
                    val inputStream = contentResolver?.openInputStream(uri)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectFile(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        launcher.launch(intent)
    }

    private fun copyFile(inputStream: InputStream?) {

    }

}