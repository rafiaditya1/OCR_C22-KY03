package com.bangkit.ocr_c22_ky03.module.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ocr_c22_ky03.module.history.HistoryActivity
import com.bangkit.ocr_c22_ky03.databinding.ActivityMainBinding
import com.bangkit.ocr_c22_ky03.module.authentication.LoginActivity
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import com.bangkit.ocr_c22_ky03.module.ktp.CameraActivity
import com.bangkit.ocr_c22_ky03.module.ktp.KtpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreference = UserPreference(this)

        binding.btnAdd.setOnClickListener{
            intent = Intent(this@MainActivity, KtpActivity::class.java)
            startActivity(intent)
        }
        binding.btnStatus.setOnClickListener{
            intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogout.setOnClickListener {
            userPreference.deleteUserLogin()
            intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}