package com.bangkit.ocr_c22_ky03.module.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.ocr_c22_ky03.databinding.ActivityHistoryBinding
import com.bangkit.ocr_c22_ky03.utils.showLoading

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

    }


    private fun setListStory() {
        viewModel.getData()
        viewModel.listData.observe(this) {
            adapter.setHistory(it)
        }
    }




}