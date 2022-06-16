package com.bangkit.ocr_c22_ky03.module.form

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean> = _message

//    fun getData() {
//        val client = ApiConfig.getApiService().get()
//        client.enqueue(object : Callback<FormResponse> {
//            override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
//                if (response.isSuccessful) {
//                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}")
//                } else {
//                    Log.e(ContentValues.TAG, "onFailure: ${response.toString()}")
//                }
//            }
//
//            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
//                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

    fun setData(
        preference: UserPreference
    ) {
        val link = preference.preference.getString("path", "")
        val client = ApiConfig.getApiService().postKtp(link.toString())
        client.enqueue(object : Callback<FormResponse> {
            override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                if (response.isSuccessful) {
                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}")
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.toString()}")
                }
            }

            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}