package com.bangkit.ocr_c22_ky03.module.history

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityHistoryBinding
import com.bangkit.ocr_c22_ky03.module.detail.DetailActivity
import com.bangkit.ocr_c22_ky03.utils.showLoading

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_appbar)

        binding.refresh.setOnRefreshListener {
            viewModel.listData.observe(this) {
                setListStory(it)
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        viewModel.listData.observe(this) {
            setListStory(it)
        }
    }

    private fun setListStory(listDataKtp: List<DataKtpResponseItem>) {
        val listUser = ArrayList<DataKtpResponseItem>()
        for (ktp in listDataKtp) {
            listUser.clear()
            listUser.addAll(listDataKtp)
            Log.d("TAG", ktp.toString())
        }
        adapter = HistoryAdapter(listUser)
        var layoutManager = LinearLayoutManager(this)
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(this, 2)
        }
        binding.rvHistory.layoutManager = layoutManager
        binding.rvHistory.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        binding.rvHistory.adapter = adapter

        adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataKtpResponseItem) {
                Intent(this@HistoryActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.DATA_KTP, data)
                    startActivity(it)
                }
            }

        })
    }
}