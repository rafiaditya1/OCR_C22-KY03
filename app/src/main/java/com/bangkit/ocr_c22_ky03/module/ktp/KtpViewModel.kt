package com.bangkit.ocr_c22_ky03.module.ktp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import com.bangkit.ocr_c22_ky03.utils.ApiCallbackString
import com.bangkit.ocr_c22_ky03.utils.UploadCallbackString
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KtpViewModel : ViewModel() {
    private val _link =MutableLiveData<UploadKtpResponse>()
    val link: LiveData<UploadKtpResponse> = _link

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun uploadImage(
        imageMultipart: MultipartBody.Part,
        callback:UploadCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .postKtp2( imageMultipart)
        client.enqueue(object : Callback<UploadKtpResponse> {
            override fun onResponse(
                call: Call<UploadKtpResponse>,
                response: Response<UploadKtpResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback.onResponse(SUCCESS, response.body().toString())
                    if (responseBody != null) {
                        Log.e(TAG, "onSukses: yele ")
                        _link.value = response.body()
                        print("sukses broo")
//                        print(message)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<UploadKtpResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                callback.onResponse(t.message.toString(), "")

            }
        })

    }

    companion object {
        private const val TAG = "ktpViewModel"
        private const val SUCCESS = "success"
    }
}