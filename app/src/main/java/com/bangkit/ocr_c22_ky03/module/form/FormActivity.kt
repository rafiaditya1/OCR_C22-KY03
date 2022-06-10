package com.bangkit.ocr_c22_ky03.module.form

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.ocr_c22_ky03.module.selfie.SelfieActivity
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.ocr_c22_ky03.databinding.ActivityFormBinding


class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private val viewModel by viewModels<FormViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFinish.setOnClickListener{
            setData(name = "Seno", nik = "123451234")
            intent = Intent(this@FormActivity, SelfieActivity::class.java)
            startActivity(intent)
        }
//        viewModel.setData(name = "Seno", nik = "123456789" ).observe(this)
    }
    private fun setData(name: String, nik: String) {
        viewModel.setData(name, nik)
    }

    companion object {
        val DATA_KTP = "data_ktp"
        val EXTRA_NIK = "extra_nik"
        val EXTRA_NAMA = "extra_nama"
        val EXTRA_TTL = "extra_ttl"
        val EXTRA_JENIS_KELAMIN = "extra_jenis_kelamin"
        val EXTRA_GOL_DARAH= "extra_gol_darah"
        val EXTRA_ALAMAT = "extra_alamar"
        val EXTRA_RT_RW = "extra_rt_rw"
        val EXTRA_KEL_DESA = "extra_kel_desa"
        val EXTRA_KECAMATAN = "extra_kecamatan"
        val EXTRA_AGAMA = "extra_agama"
        val EXTRA_STATUS_PERKAWINAN = "extra_status_perkawinan"
        val EXTRA_PEKERJAAN = "extra_pekerjaan"
        val EXTRA_KEWARGANEGARAAN = "extra_kewarganegaraan"
    }
}