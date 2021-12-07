package com.example.aiedudemo.main

import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.example.aiedudemo.view.DrawSurfaceView

class DrawSelf : AppCompatActivity(){

    var surfaceViewWidth: Int? = null
    var surfaceViewHeight: Int? = null
    var drawSurfaceView: DrawSurfaceView? = null

    /// 拡張関数
    // ViewTreeObserverを使ってViewが作成されてからsurfaceViewのサイズ取得
    private inline fun <T : View> T.afterMeasure(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (width > 0 && height > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawself)

        var surfaceView = findViewById<View>(R.id.surfaceView)
        var resetBtn = findViewById<Button>(R.id.resetBtn)
        var saveBtn = findViewById<Button>(R.id.saveBtn)
        var pics = findViewById<TextView>(R.id.picsnum)

        /// ViewTreeObserberを使用
        /// surfaceViewが生成し終わってからsurfaceViewのサイズを取得
        surfaceView.afterMeasure {
            surfaceViewWidth = surfaceView.width
            surfaceViewHeight = surfaceView.height
            //// DrawrSurfaceViewのセットとインスタンス生成
            drawSurfaceView = DrawSurfaceView(
                applicationContext,
                surfaceView as SurfaceView,
                surfaceViewWidth!!,
                surfaceViewHeight!!
            )
            /// リスナーのセット
            surfaceView.setOnTouchListener { v, event -> drawSurfaceView!!.onTouch(event) }
        }

        /// リセットボタン
        resetBtn.setOnClickListener {
            drawSurfaceView!!.reset()   /// bitmap初期化メソッドを呼び出す
        }

        /// 画像の保存
        saveBtn.setOnClickListener {
            var x = pics.text.toString()
            var add = x.toInt() + 1

            if(add <= 5){
                pics.text = add.toString()
                drawSurfaceView!!.reset()
            }else {

            }
        }
    }
}