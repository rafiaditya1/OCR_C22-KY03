package com.bangkit.ocr_c22_ky03.module.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ocr_c22_ky03.module.history.HistoryActivity
import com.bangkit.ocr_c22_ky03.module.ktp.KtpActivity
import com.bangkit.ocr_c22_ky03.databinding.ActivityMainBinding
import com.bangkit.ocr_c22_ky03.module.ktp.CameraActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{
            intent = Intent(this@MainActivity, CameraActivity::class.java)
            startActivity(intent)
        }
        binding.btnStatus.setOnClickListener{
            intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}