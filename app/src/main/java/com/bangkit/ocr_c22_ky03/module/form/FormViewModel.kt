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
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponseItem
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

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean> = _message

    fun getData(preference: UserPreference) {
        val a = preference.preference.getString("path", "").toString()
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
        preference: UserPreference,
        nik: String,
        nama: String,
        tempat: String,
        tgl_lahir: String,
        jenis_kelamin: String,
        alamat: String,
        agama: String,
        status_perkawinan: String,
        pekerjaan: String,
        kewarganegaraan: String
    ) {
        val idKtp = preference.preference.getInt("id", 0)
        val client = ApiConfig.getApiService().setForm(
            idKtp, nik, nama, tempat, tgl_lahir,
            jenis_kelamin, alamat, agama, status_perkawinan,
            pekerjaan, kewarganegaraan
        )
        client.enqueue(object : Callback<DataKtpResponseItem> {
            override fun onResponse(
                call: Call<DataKtpResponseItem>,
                response: Response<DataKtpResponseItem>
            ) {
                if (response.isSuccessful) {
                    Log.e(ContentValues.TAG, "onResponse: ${response.message()}")
                    Log.e(ContentValues.TAG, "Berhasil Upload Data")
                    Log.e(ContentValues.TAG, "Berhasil Upload Data")
                    Log.e(ContentValues.TAG, "Berhasil Upload Data")
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    Log.e(ContentValues.TAG, "Gagal Upload Data")
                    Log.e(ContentValues.TAG, "gagal Upload Data")
                    Log.e(ContentValues.TAG, "gagal Upload Data")
                }
            }

            override fun onFailure(call: Call<DataKtpResponseItem>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                Log.e(ContentValues.TAG, "gagal Upload Data")
                Log.e(ContentValues.TAG, "gagal Upload Data")
                Log.e(ContentValues.TAG, "gagal Upload Data")
            }
        })
    }
}