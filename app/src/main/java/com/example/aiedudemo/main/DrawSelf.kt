package com.example.aiedudemo.main

//import com.divyanshu.draw.widget.DrawView

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.example.aiedudemo.view.DrawCanvas


class DrawSelf : AppCompatActivity(){
    private var drawView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)

        drawView?.findViewById<View>(R.id.draw_view)

        DrawCanvas(this)

    }
}