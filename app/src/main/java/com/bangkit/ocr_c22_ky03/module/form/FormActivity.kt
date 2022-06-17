package com.bangkit.ocr_c22_ky03.module.form

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityFormBinding
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import com.bangkit.ocr_c22_ky03.module.selfie.SelfieActivity
import com.bangkit.ocr_c22_ky03.utils.ApiCallbackString
import com.bangkit.ocr_c22_ky03.utils.showLoading


class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private val viewModel by viewModels<FormViewModel>()
    private lateinit var pathPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pathPreference = UserPreference(this)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_appbar)

        viewModel.getData(pathPreference)
        viewModel.dataKtp.observe(this) {
            binding.edtNik.setText(it.nik)
            binding.edtNama.setText(it.nama)
            binding.edtTempatTglLahir.setText(it.ttl)
            binding.edtJenisKelamin.setText(it.jenis)
            binding.edtAgama.setText(it.agama)
            binding.edtStatusPerkawinan.setText(it.status)
            binding.edtPekerjaan.setText(it.pekerjaan)
            binding.edtKewarganegaraan.setText(it.kwn)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }
        val nik = binding.edtNik.text.toString()
        val nama = binding.edtNama.text.toString()
        val tempatTanggalLahir = binding.edtTempatTglLahir.text.toString()
        val jenisKelamin = binding.edtJenisKelamin.text.toString()
        val alamat = binding.edtAlamat.text.toString()
        val kelDesa = binding.edtKelDesa.text.toString()
        val kelurahan = binding.edtKecamatan.text.toString()
        val agama = binding.edtAgama.text.toString()
        val status = binding.edtStatusPerkawinan.text.toString()
        val pekrjaan = binding.edtPekerjaan.text.toString()
        val kewarganegaraan = binding.edtKewarganegaraan.text.toString()

        binding.btnFinish.setOnClickListener {
            viewModel.setData(
                pathPreference,
                nik,
                nama,
                tempatTanggalLahir,
                tempatTanggalLahir,
                jenisKelamin,
                agama,
                status,
                pekrjaan,
                kewarganegaraan,
                object : ApiCallbackString {
                    override fun onResponse(msg: String) {
                        if (msg == "Data saved on Database") {
                            val a = true
                            showAlertDialog(a, msg)
                        } else {
                            val a = false
                            showAlertDialog(a, msg)
                        }
                    }
                }
            )
            viewModel.isLoading2.observe(this) {
                showLoading(it, binding.progressBar2)
            }
        }
    }

    private fun showAlertDialog(param: Boolean, status: String) {
        if (param) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information_title))
                setMessage(getString(R.string.upload_success))
                setPositiveButton(getString(R.string.btn_continue)) { _, _ ->
                    val intent = Intent(this@FormActivity, SelfieActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information_title))
                setMessage(getString(R.string.upload_failed) + ", $status")
                setPositiveButton(getString(R.string.btn_continue)) { _, _ ->
                    binding.progressBar.visibility = View.GONE
                }
                create()
                show()
            }
        }
    }
}