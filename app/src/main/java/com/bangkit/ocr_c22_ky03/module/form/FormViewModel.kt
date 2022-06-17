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
import com.bangkit.ocr_c22_ky03.utils.ApiCallbackString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class FormViewModel : ViewModel() {

    private var _dataKtp = MutableLiveData<Ktp2Response>()
    val dataKtp: LiveData<Ktp2Response> = _dataKtp

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _isLoading2 = MutableLiveData<Boolean>()
    val isLoading2: LiveData<Boolean> = _isLoading2

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean> = _message

    fun getData(preference: UserPreference) {
        _isLoading.value = true
        val a = preference.preference.getString("path", "").toString()
        val b = "a3RwXzg5LmpwZw=="
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
        val client = ApiConfig.getMLApiService().getKtp(b)
        client.enqueue(object : Callback<Ktp2Response> {
            override fun onResponse(call: Call<Ktp2Response>, response: Response<Ktp2Response>) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}")
                    _dataKtp.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure1: $response")
                }
            }

            override fun onFailure(call: Call<Ktp2Response>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure2: ${t.message.toString()}")
            }

        })
    }

    fun setData(
//        preference: UserPreference,
//        nik: String,
        nama: String,
//        tempat: String,
//        tgl_lahir: String,
//        jenis_kelamin: String,
////        alamat: String,
//        agama: String,
//        status_perkawinan: String,
//        pekerjaan: String,
//        kewarganegaraan: String,
        callback: ApiCallbackString
    ) {
        _isLoading2.value = true
//        val idKtp = preference.preference.getInt("id", 0)
        val a: Int = 100

        val client = ApiConfig.getApiService().setForm(
//            a, nik,
            nama
//            tempat, tgl_lahir,
//            jenis_kelamin, agama, status_perkawinan,
//            pekerjaan, kewarganegaraan
        )
        client.enqueue(object : Callback<DataResponseItem> {
            override fun onResponse(
                call: Call<DataResponseItem>,
                response: Response<DataResponseItem>
            ) {
                _isLoading2.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback.onResponse(response.body().toString())
                    if (responseBody != null) {
                        Log.e("TAG", "onSukses: yele ")
                        print("sukses broo")
//                        print(message)
                    }
                } else {
                    val e = Log.e("TAG", "onFailure: ${response.message()}")

                }

            }

            override fun onFailure(call: Call<DataResponseItem>, t: Throwable) {
                _isLoading.value = false
                Log.e("TAG2", "onFailure: ${t.message}")
                callback.onResponse(t.message.toString())
            }
        })
    }
}