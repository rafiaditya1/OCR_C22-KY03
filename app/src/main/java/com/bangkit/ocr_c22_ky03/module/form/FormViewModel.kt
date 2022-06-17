package com.bangkit.ocr_c22_ky03.module.form

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import java.util.*

class FormViewModel : ViewModel() {

    private var _dataKtp = MutableLiveData<Ktp2Response>()
    val dataKtp: LiveData<Ktp2Response> = _dataKtp

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean> = _message

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(preference: UserPreference) {
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
        print("ini PATH "+link)
        val client = ApiConfig.getMLApiService().getKtp(link)
        client.enqueue(object : Callback<Ktp2Response> {
            override fun onResponse(call: Call<Ktp2Response>, response: Response<Ktp2Response>) {
                if (response.isSuccessful) {
                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}")
                    _dataKtp.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.toString()}")
                }
            }
            override fun onFailure(call: Call<Ktp2Response>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

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