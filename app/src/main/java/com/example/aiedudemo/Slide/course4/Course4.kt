package com.example.aiedudemo.Slide.course4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.aiedudemo.R
import com.example.aiedudemo.adapter.AdapterCourse4

class Course4: AppCompatActivity(){
    private var viewPager4: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course4)
        viewPager4 = findViewById(R.id.viewPager4)


        /// adapterのインスタンス生成
        val adapter = AdapterCourse4(this)
        /// adapterをセット
        viewPager4?.adapter = adapter
    }

}