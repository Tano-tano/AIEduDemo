package com.example.aiedudemo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.aiedudemo.R
import com.example.aiedudemo.Slide.course1.Course1
import com.example.aiedudemo.Slide.course2.Course2
import com.example.aiedudemo.Slide.course3.Course3
import com.example.aiedudemo.Slide.course4.Course4

class MenuStudy : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_menu_study, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = arrayOf("深層学習とは", "深層学習の使用例", "画像認識について", "画像認識精度")
        val listview = view.findViewById<ListView>(R.id.list_study)

        val adapter = ArrayAdapter(this.requireContext(),//Fragment上でContextを持ってくるにはrequireContext()が必要
            android.R.layout.simple_list_item_1,
            array
        )

        listview.adapter = adapter
        listview.setOnItemClickListener { _, _, position, _ ->
            when(position){
                0 ->{
                    val intent = Intent(this.requireContext(), Course1::class.java)
                    startActivity(intent)
                }
                1 ->{
                    val intent = Intent(this.requireContext(), Course2::class.java)
                    startActivity(intent)
                }
                2 ->{
                    val intent = Intent(this.requireContext(), Course3::class.java)
                    startActivity(intent)
                }
                3 ->{
                    val intent = Intent(this.requireContext(), Course4::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}