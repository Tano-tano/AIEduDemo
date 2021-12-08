package com.example.aiedudemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View

class Palette: View{
    private var paint: Paint? = null
    private var path: Path? = null

    fun CanvasTest2View(context: Context?) {
        super(context)
        path = Path()
        paint = Paint()
        paint!!.color = -0xff7800
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeJoin = Paint.Join.ROUND
        paint!!.strokeCap = Paint.Cap.ROUND
        paint!!.strokeWidth = 10F
    }

    override fun onDraw(canvas: Canvas) {
        path?.let { paint?.let { it1 -> canvas.drawPath(it, it1) } }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x: Float = event.x
        val y: Float = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path?.moveTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                path?.lineTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                path?.lineTo(x, y)
                invalidate()
            }
        }
        return true
    }

}