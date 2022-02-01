package com.example.aiedudemo.Slide.course1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.aiedudemo.R
import com.example.aiedudemo.adapter.AdapterCourse1
import com.example.aiedudemo.fragments.Course1.c1p1
import com.example.aiedudemo.fragments.Course1.c1p2
import com.example.aiedudemo.fragments.Course1.c1p3
import com.example.aiedudemo.fragments.Course1.c1p4

class Course1: AppCompatActivity() {
    private var viewPager1: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course1)
        viewPager1 = findViewById(R.id.viewPager1)

        /// フラグメントのリストを作成
        val fragmentList = arrayListOf<Fragment>(
            c1p1(),
            c1p2(),
            c1p3(),
            c1p4()
        )

        /// adapterのインスタンス生成
        //val adapter = AdapterCourse1(supportFragmentManager, fragmentList)
        /// adapterをセット

        var adapter = AdapterCourse1(this)
        viewPager1?.adapter = adapter
    }
}