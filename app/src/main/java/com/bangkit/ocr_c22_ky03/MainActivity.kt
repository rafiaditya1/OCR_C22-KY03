package com.bangkit.ocr_c22_ky03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ocr_c22_ky03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{
            intent = Intent(this@MainActivity, KtpActivity::class.java)
            startActivity(intent)
        }
        binding.btnStatus.setOnClickListener{
            intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}