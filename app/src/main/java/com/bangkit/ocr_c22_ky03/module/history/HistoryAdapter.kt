package com.bangkit.ocr_c22_ky03.module.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ItemRowHistoryBinding
import com.bumptech.glide.Glide

class HistoryAdapter(private val listHistory: List<DataKtpResponseItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(var binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = listHistory[position]
        with(holder.binding) {
            when (history.status) {
                "diterima" -> {
                    Glide.with(root.context)
                        .load(R.drawable.sts_verified)
                        .into(imgSts)
                }
                "menunggu" -> {
                    Glide.with(root.context)
                        .load(R.drawable.sts_process)
                        .into(imgSts)

                }
                else -> {
                    Glide.with(root.context)
                        .load(R.drawable.sts_rejected)
                        .into(imgSts)
                }
            }
            tvName.text = history.name
            tvDate.text = history.createdAt
            root.setOnClickListener { onItemClickCallback?.onItemClicked((listHistory[position])) }
        }
    }

    override fun getItemCount(): Int = listHistory.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataKtpResponseItem)
    }
}