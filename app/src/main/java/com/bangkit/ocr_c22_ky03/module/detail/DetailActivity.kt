package com.bangkit.ocr_c22_ky03.module.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityDetailBinding
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponseItem

class DetailActivity : AppCompatActivity() {
    private lateinit var histoy: DataKtpResponseItem
    private lateinit var binding: ActivityDetailBinding
    private val viewmodel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.custom_appbar)


        histoy = intent.getParcelableExtra(DATA_KTP)!!
        viewmodel.setDetailStory(histoy)
        displayResult()
    }

    private fun displayResult() {
        with(binding){
            tvStatus.text = viewmodel.historyItem.status
            tvNik.text = viewmodel.historyItem.nik
            tvName.text = viewmodel.historyItem.name
            tvBirth.text = viewmodel.historyItem.tglLahir
            tvGender.text = viewmodel.historyItem.jenisKelamin
            tvBlood.text = viewmodel.historyItem.golDarah
            tvAddress.text = viewmodel.historyItem.alamat
            tvReligion.text = viewmodel.historyItem.agama
            tvMaritalSts.text = viewmodel.historyItem.statusPerkawinan
            tvJob.text = viewmodel.historyItem.pekerjaan
            tvCitizenship.text = viewmodel.historyItem.kewarganegaraan

//            Glide.with(imgPhoto)
//                .load(viewmodel.storyItem.photoUrl) // URL Avatar
//                .placeholder(R.drawable.ic_baseline_image_gray)
//                .error(R.drawable.ic_baseline_broken_image_gray)
//                .into(imgPhoto)
        }
    }

    companion object {
        const val DATA_KTP = "data_ktp"
    }
}