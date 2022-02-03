package com.example.aiedudemo.Slide.course1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

        var adapter = AdapterCourse1(this)
        viewPager1?.adapter = adapter
    }
}