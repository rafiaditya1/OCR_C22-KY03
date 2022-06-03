package com.bangkit.ocr_c22_ky03.module.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean> = _message

    fun getData() {

    }

    fun setData(
        name: String,
        nik: String
    ) {
        val client = ApiConfig.getApiService().postKtp(name, nik)
        client.enqueue(object : Callback<FormResponse> {
            override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                if (response.isSuccessful){
                    val responseBody = response.body()

                }
            }

            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}