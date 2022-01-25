package com.example.roomexplore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv)
        setAdapter()
    }

    fun setAdapter() {
        val wordAdapter = WordAdapter()
        recyclerView.adapter = wordAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}