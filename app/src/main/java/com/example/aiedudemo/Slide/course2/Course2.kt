package com.example.aiedudemo.Slide.course2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.aiedudemo.R
import com.example.aiedudemo.adapter.AdapterCourse2

class Course2: AppCompatActivity(){
    private var viewPager2: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course2)
        viewPager2 = findViewById(R.id.viewPager2)

        /// adapterのインスタンス生成
        val adapter = AdapterCourse2(this)
        /// adapterをセット
        viewPager2?.adapter = adapter
    }
}