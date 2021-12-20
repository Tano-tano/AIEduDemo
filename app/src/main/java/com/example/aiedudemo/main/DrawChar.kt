package com.example.aiedudemo.main

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.divyanshu.draw.widget.DrawView
import com.example.aiedudemo.R

class DrawChar : AppCompatActivity(){
    private var drawView: DrawView? = null
    private var resetButton: Button? = null
    private var inferButton: Button? = null
    private var resultnum: TextView? = null


    private var charClassifier = CharClassifier(this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawchar)

        drawView = findViewById(R.id.draw_view)
        drawView?.setStrokeWidth(70.0f)
        drawView?.setColor(Color.WHITE)
        drawView?.setBackgroundColor(Color.BLACK)
        resetButton = findViewById(R.id.resetBtn)
        inferButton = findViewById(R.id.inferBtn)
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

        charClassifier
            .initialize()
            .addOnFailureListener{e -> Log.e(ContentValues.TAG, "Error", e)}
    }

    override fun onDestroy() {
        // Sync DigitClassifier instance lifecycle with MainActivity lifecycle,
        // and free up resources (e.g. TF Lite instance) once the activity is destroyed.
        charClassifier.close()
        super.onDestroy()
    }

    private fun classifyDrawing() {
        val bitmap = drawView?.getBitmap()

        if ((bitmap != null) && (charClassifier.isInitialized)) {
            charClassifier
                .classifyAsync(bitmap)
                .addOnSuccessListener { resultText -> resultnum?.text = resultText }
                .addOnFailureListener { e ->
                    resultnum?.text = "-"
                    Log.e(ContentValues.TAG, "Error classifying drawing.", e)
                }
        }
    }

}