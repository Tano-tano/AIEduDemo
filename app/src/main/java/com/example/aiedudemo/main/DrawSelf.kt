package com.example.aiedudemo.main

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import com.divyanshu.draw.widget.DrawView
import com.mtjin.library.DrawView
import com.example.aiedudemo.R
import com.example.aiedudemo.view.DrawSurfaceView

class DrawSelf : AppCompatActivity(){
    private var drawView: DrawView? = null
    private var resetButton: Button? = null
    private var saveButton: Button? = null
    private var pics: TextView? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)

        drawView = findViewById(R.id.draw_view)
        drawView?.setStrokeWidth(70.0f)
        drawView?.setPenColor(Color.WHITE)
        drawView?.setBackgroundColor(Color.BLACK)
        resetButton = findViewById(R.id.resetBtn)
        saveButton = findViewById(R.id.saveBtn)
        pics = findViewById(R.id.picsnum)


        /// リセットボタン
        resetButton?.setOnClickListener {
            drawView?.clear()  /// bitmap初期化メソッドを呼び出す
        }

        /// 画像の保存
        saveButton?.setOnClickListener {
            var x = pics?.text.toString()
            var add = x.toInt() + 1

            if(add <= 5){
                pics?.text = add.toString()
                var x = drawView?.saveFileDrawViewGetUri()
                Log.d(TAG,"===============$x=================")
                drawView?.clear()
            }else {

            }
        }
    }
}