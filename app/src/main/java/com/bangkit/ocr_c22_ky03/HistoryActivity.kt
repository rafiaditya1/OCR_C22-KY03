package com.bangkit.ocr_c22_ky03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ocr_c22_ky03.dummy.ListHistoryAdapter
import com.bangkit.ocr_c22_ky03.dummy.Users
import com.bangkit.ocr_c22_ky03.dummy.UsersData
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {
    private lateinit var rvHistory: RecyclerView
    private val list = ArrayList<Users>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        rvHistory = findViewById(R.id.rv_history)
        rvHistory.setHasFixedSize(true)

        list.addAll(UsersData.listData)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvHistory.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHistoryAdapter(list)
        rvHistory.adapter = listHeroAdapter
//
        listHeroAdapter.setOnItemClickCallback(object : ListHistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                intent = Intent(this@HistoryActivity, DetailActivity::class.java)
                startActivity(intent)
            }
        })
    }



}