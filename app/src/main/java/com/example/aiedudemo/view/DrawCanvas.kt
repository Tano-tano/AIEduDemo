package com.example.aiedudemo.view

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View


class DrawCanvas(context: Context?) : View(context) {
    private var paint: Paint? = null
    private var pathList: ArrayList<Path> = ArrayList<Path>()

    fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP -> {}
            else -> {}
        }
        return true
    }
}