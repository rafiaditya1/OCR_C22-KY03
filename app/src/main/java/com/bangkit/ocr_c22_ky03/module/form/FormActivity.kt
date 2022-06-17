package com.bangkit.ocr_c22_ky03.module.form

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.ocr_c22_ky03.module.selfie.SelfieActivity
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.ocr_c22_ky03.databinding.ActivityFormBinding
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference


class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private val viewModel by viewModels<FormViewModel>()
    private lateinit var pathPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pathPreference = UserPreference(this)

        val nik = binding.edtNik.text.toString()
        val nama = binding.edtNama.text.toString()
        val agama = binding.edtAgama.text.toString()

        viewModel.dataKtp.observe(this) {
            val nik = it.nik.toString()

            binding.edtNik.setText(it.nik)

        }

        binding.btnFinish.setOnClickListener {
            viewModel.setData(pathPreference)
            intent = Intent(this@FormActivity, SelfieActivity::class.java)
            startActivity(intent)
        }
    }
}