package com.bangkit.ocr_c22_ky03.module.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean> = _message

    fun getData() {

    }

    fun setData() {

    }

}