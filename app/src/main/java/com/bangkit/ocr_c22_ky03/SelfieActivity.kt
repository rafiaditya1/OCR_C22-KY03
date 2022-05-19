package com.bangkit.ocr_c22_ky03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.ocr_c22_ky03.databinding.ActivitySelfieBinding

class SelfieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelfieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, ResultSelfieActivity::class.java))
            finish()
        }
    }

}