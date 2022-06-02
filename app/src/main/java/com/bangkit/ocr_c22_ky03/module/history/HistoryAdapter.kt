package com.bangkit.ocr_c22_ky03.module.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ItemRowHistoryBinding
import com.bangkit.ocr_c22_ky03.module.detail.DetailActivity
import com.bangkit.ocr_c22_ky03.utils.DiffCallback
import com.bumptech.glide.Glide

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    private val listHistory = ArrayList<DataKtpResponseItem>()

    fun setHistory(itemStory: List<DataKtpResponseItem>) {
        val diffCallback = DiffCallback(this.listHistory, itemStory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listHistory.clear()
        this.listHistory.addAll(itemStory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listHistory[position])

    }

    override fun getItemCount() = listHistory.size

    class ViewHolder(private var binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val status: ImageView = itemView.findViewById(R.id.img_sts)

        fun bind(history: DataKtpResponseItem) {
            when (history.status) {
                "diterima" -> {
                    Glide.with(itemView.context)
                        .load(R.drawable.sts_verified)
                        .into(status)
                }
                "menunggu" -> {
                    Glide.with(itemView.context)
                        .load(R.drawable.sts_process)
                        .into(status)

                }
                else -> {
                    Glide.with(itemView.context)
                        .load(R.drawable.sts_rejected)
                        .into(status)
                }
            }
            with(binding) {
                tvName.text = history.name
                tvDate.text = history.createdAt

                btnDetail.setOnClickListener {
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            btnDetail.context as Activity,
//                            Pair(imgImage, "profile"),
//                            Pair(tvName, "name"),
//                        )
                    val intent = Intent(btnDetail.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_HISTORY, history)
                    btnDetail.context.startActivity(intent)
                }


            }
        }

    }
}