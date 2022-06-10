package com.bangkit.ocr_c22_ky03.module.detail

import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponseItem

class DetailViewModel : ViewModel() {
    lateinit var historyItem: DataKtpResponseItem

    fun setDetailStory(history: DataKtpResponseItem) : DataKtpResponseItem{
        historyItem = history
        return historyItem
    }
}