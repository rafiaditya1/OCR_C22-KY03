package com.bangkit.ocr_c22_ky03.module.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ocr_c22_ky03.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}