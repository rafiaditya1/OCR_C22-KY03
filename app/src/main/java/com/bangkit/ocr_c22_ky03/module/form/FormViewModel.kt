package com.bangkit.ocr_c22_ky03.module.form

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FormViewModel : ViewModel() {

    private var _dataKtp = MutableLiveData<Ktp2Response>()
    val dataKtp: LiveData<Ktp2Response> = _dataKtp

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isLoading2 = MutableLiveData<Boolean>()
    val isLoading2: LiveData<Boolean> = _isLoading2


    fun getData(preference: UserPreference) {
        _isLoading.value = true
        val a = preference.preference.getString("path", "").toString()
        val encoder: Base64.Encoder = Base64.getEncoder()
        val link: String = encoder.encodeToString(a.toByteArray())
        val client = ApiConfig.getMLApiService().getKtp(link)
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
        preference: UserPreference,
        nik: String,
        nama: String,
        tempat: String,
        tgl_lahir: String,
        jenis_kelamin: String,
        agama: String,
        status_perkawinan: String,
        pekerjaan: String,
        kewarganegaraan: String,
    ) {
        _isLoading2.value = true
        val idKtp = preference.preference.getInt("id", 0)
        val client = ApiConfig.getApiService().setForm(
            idKtp,
            nik,
            nama,
            tempat,
            tgl_lahir,
            jenis_kelamin,
            null,
            null,
            agama,
            status_perkawinan,
            pekerjaan,
            kewarganegaraan, null, null, null
        )
        client.enqueue(object : Callback<DataKtpResponseItem> {
            override fun onResponse(
                call: Call<DataKtpResponseItem>,
                response: Response<DataKtpResponseItem>
            ) {
                _isLoading2.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("Berhasil", "onSukses: ${response.message()}")
                } else {
                    Log.e("Tidak Berhasil", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DataKtpResponseItem>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed", "onFailure: ${t.message}")
            }
        })
    }
}