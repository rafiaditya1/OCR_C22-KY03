package com.bangkit.ocr_c22_ky03.module.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ocr_c22_ky03.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
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