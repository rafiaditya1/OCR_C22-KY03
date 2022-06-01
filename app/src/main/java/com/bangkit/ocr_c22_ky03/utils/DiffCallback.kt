package com.bangkit.ocr_c22_ky03.utils

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponseItem

class DiffCallback(
    private val mOldFavList: List<DataKtpResponseItem>,
    private val mNewFavList: List<DataKtpResponseItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = mOldFavList.size

    override fun getNewListSize() = mNewFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.id == newEmployee.id
    }
}