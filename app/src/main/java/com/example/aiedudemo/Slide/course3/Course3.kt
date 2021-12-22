package com.example.aiedudemo.Slide.course3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.aiedudemo.R
import com.example.aiedudemo.adapter.AdapterCourse3

class Course3 : AppCompatActivity(){
    private var viewPager3: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course3)
        viewPager3 = findViewById(R.id.viewPager3)


        /// adapterのインスタンス生成
        val adapter = AdapterCourse3(this)
        /// adapterをセット
        viewPager3?.adapter = adapter
    }

}