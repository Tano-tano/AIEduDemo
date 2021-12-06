package com.example.aiedudemo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.aiedudemo.R
import com.example.aiedudemo.main.DrawChar
import com.example.aiedudemo.main.DrawDigit
import com.example.aiedudemo.main.DrawSelf

class MenuEXP : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_menu_experiment, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = arrayOf("数字認識をしてみよう", "文字認識をしてみよう", "訓練用画像を学習させてみよう")
        val listview = view.findViewById<ListView>(R.id.list_experiment)

        val adapter = ArrayAdapter(this.requireContext(),//Fragment上でContextを持ってくるにはrequireContext()が必要
            android.R.layout.simple_list_item_1,
            array
        )

        listview.adapter = adapter
        listview.setOnItemClickListener { _, _, position, _ ->
            when(position){
                0 ->{
                    val intent = Intent(this.requireContext(), DrawDigit::class.java)
                    startActivity(intent)
                }
                1 ->{
                    val intent = Intent(this.requireContext(), DrawChar::class.java)
                    startActivity(intent)
                }
                2 ->{
                    val intent = Intent(this.requireContext(), DrawSelf::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}