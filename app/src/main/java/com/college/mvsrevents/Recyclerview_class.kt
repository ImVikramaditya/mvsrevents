package com.college.mvsrevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class Recyclerview_class : AppCompatActivity() {
    lateinit var view_recycle: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        view_recycle = findViewById(R.id.view_recycle)
        layoutManager = LinearLayoutManager(this)

    }
}
