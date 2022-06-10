package com.bangkit.ocr_c22_ky03.module.history

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listData = MutableLiveData<List<DataKtpResponseItem>>()
    val listData: LiveData<List<DataKtpResponseItem>> = _listData


    init {
        getData()
    }

    private fun getData(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getHistory()
        client.enqueue(object : Callback<List<DataKtpResponseItem>>{
            override fun onResponse(
                call: Call<List<DataKtpResponseItem>>,
                response: Response<List<DataKtpResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listData.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DataKtpResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}