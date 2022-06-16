package com.bangkit.ocr_c22_ky03.module.authentication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.ocr_c22_ky03.api.ApiConfig
import com.bangkit.ocr_c22_ky03.api.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private var _user = MutableLiveData<LoginResponse>()
    val user: LiveData<LoginResponse> = _user

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private var _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error


    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _message.value = response.message()
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message.toString()
                _error.value = true
            }
        })
    }

}