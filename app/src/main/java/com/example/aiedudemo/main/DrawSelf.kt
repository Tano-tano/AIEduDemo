package com.example.aiedudemo.main

//import com.divyanshu.draw.widget.DrawView
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.mtjin.library.DrawView

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
                var y = x?.let { it1 -> getFileName(this, it1) }
                Log.d(TAG,"===============$y=================")
                drawView?.clear()
            }else {

            }
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {

        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }

        return uri.path?.substring(uri.path!!.lastIndexOf('/') + 1)
    }



}