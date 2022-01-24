package com.example.aiedudemo.main

import android.annotation.SuppressLint
import android.content.ContentValues
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
    private var drawView: DrawView? = null
    private var resetButton: Button? = null
    private var inferButton: Button? = null
    private var backButton: Button? = null
    private var resultnum: TextView? = null

    private var selfClassifier = SelfClassifier(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.importmodel)

        drawView = findViewById(R.id.draw_view)
        drawView?.setStrokeWidth(70.0f)
        drawView?.setColor(Color.WHITE)
        drawView?.setBackgroundColor(Color.BLACK)
        resetButton = findViewById(R.id.resetBtn)
        inferButton = findViewById(R.id.inferBtn)
        backButton = findViewById(R.id.backBtn)
        resultnum = findViewById(R.id.resultNum)

        //リセットボタンの処理
        resetButton?.setOnClickListener {
            drawView?.clearCanvas()
            resultnum?.text = "?"
        }

        //描画処理
        drawView?.setOnTouchListener{ _, event ->
            drawView?.onTouchEvent(event)
            true
        }

        //推定処理
        inferButton?.setOnClickListener {
            classifyDrawing()
        }

        //戻るボタン
        backButton?.setOnClickListener {
            finish()
        }

        selfClassifier
            .initialize()
            .addOnFailureListener{e -> Log.e(ContentValues.TAG, "Error", e)}

    }

    override fun onDestroy() {
        // Sync DigitClassifier instance lifecycle with MainActivity lifecycle,
        // and free up resources (e.g. TF Lite instance) once the activity is destroyed.
        selfClassifier.close()
        super.onDestroy()
    }

    private fun classifyDrawing() {
        val bitmap = drawView?.getBitmap()

        if ((bitmap != null) && (selfClassifier.isInitialized)) {
            selfClassifier
                .classifyAsync(bitmap)
                .addOnSuccessListener { resultText -> resultnum?.text = resultText }
                .addOnFailureListener { e ->
                    resultnum?.text = "-"
                    Log.e(ContentValues.TAG, "Error classifying drawing.", e)
                }
        }
    }
}