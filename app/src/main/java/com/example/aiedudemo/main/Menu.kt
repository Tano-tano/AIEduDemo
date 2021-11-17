package com.example.aiedudemo.main

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.aiedudemo.R
import com.example.aiedudemo.adapter.TabAdapter

class Menu : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        .adapter = TabAdapter(supportFragmentManager,this)
        tab_layout.setupWithViewPager(pager)

//        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1).apply {
//            add("機械学習とは")
//            add("手書き数字認識をしてみよう")
//            add("手書き文字認識をしてみよう")
//            add("ニューラルネットワークを作成してみよう")
//        }
//        val listView: ListView = findViewById(R.id.menu_list)
//        listView.adapter = arrayAdapter
    }
}

