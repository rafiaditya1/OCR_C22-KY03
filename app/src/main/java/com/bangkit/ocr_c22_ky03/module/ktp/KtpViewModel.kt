package com.bangkit.ocr_c22_ky03.module.ktp

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import com.bangkit.ocr_c22_ky03.module.form.FormResponse
import com.bangkit.ocr_c22_ky03.utils.UploadCallbackString
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import java.util.*

class KtpViewModel : ViewModel() {
    private val _link = MutableLiveData<UploadKtpResponse>()
    val link: LiveData<UploadKtpResponse> = _link
    val abcd = MutableLiveData<UploadKtpResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun uploadImage(
        imageMultipart: MultipartBody.Part,
        callback: UploadCallbackString
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .postKtp2(imageMultipart)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun postPath(
        preference: UserPreference
    ) {
        var a = preference.preference.getString("path", "").toString()
//        a = a?.replace(".jpg", "")
        val ENCODED_HREF = URLEncoder.encode(a, "utf-8")

        val string = "Some text"

        // encode a string using Base64 encoder
        val encoder: Base64.Encoder = Base64.getEncoder()
        val link: String = encoder.encodeToString(a.toByteArray())
        val encoded2: String = encoder.encodeToString(link.toByteArray())
//        val ENCODED_HREF2 = URLEncoder.encode(encoded, "utf-8")
        println("Encoded Data: $link")

        Log.e("PATH", a)
        Log.e("PATH ENCODED", link)
        print("ini PATH " + link)
        val client = ApiConfig.getMLApiService().postKtp(link)
        client.enqueue(object : Callback<FormResponse> {
            override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                if (response.isSuccessful) {
                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}")
//                    abcd.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: $response")
                }
            }

            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "ktpViewModel"
        private const val SUCCESS = "success"
    }
}