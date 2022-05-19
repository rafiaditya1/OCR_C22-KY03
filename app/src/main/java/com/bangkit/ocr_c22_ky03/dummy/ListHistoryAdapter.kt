package com.bangkit.ocr_c22_ky03.dummy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ocr_c22_ky03.R
import java.util.ArrayList

class ListHistoryAdapter(private val listHero: ArrayList<Users>) : RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_history, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val hero = listHero[position]
        holder.tvName.text = hero.name
        holder.tvDetail.text = hero.date

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHero[holder.adapterPosition]) }
    }


    override fun getItemCount(): Int {
        return listHero.size
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_date)

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}