package com.example.aiedudemo.main

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.example.aiedudemo.view.DrawCanvas


class DrawSelf : AppCompatActivity(){
    private var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)
        val context: Context = applicationContext

        //５）Viewの取得⇒クリア処理
        val drawView: DrawCanvas = findViewById(R.id.draw_view)
        val resetBtn: Button = findViewById(R.id.resetBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)
        val picsNum: TextView = findViewById(R.id.picsnum)

        picsNum.text = page.toString()

        resetBtn.setOnClickListener {
            drawView.clearCanvas()
        }

        saveBtn.setOnClickListener {
            page += 1
            picsNum.text = page.toString()
            drawView.clearCanvas()
        }
    }
}