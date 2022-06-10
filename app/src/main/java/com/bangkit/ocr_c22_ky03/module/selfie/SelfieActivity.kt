package com.bangkit.ocr_c22_ky03.module.selfie

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.ocr_c22_ky03.utils.bitmapToFile
import com.bangkit.ocr_c22_ky03.databinding.ActivitySelfieBinding
import com.bangkit.ocr_c22_ky03.module.history.HistoryActivity
import com.bangkit.ocr_c22_ky03.utils.rotateBitmap
import com.bangkit.ocr_c22_ky03.utils.toSquare
import com.bumptech.glide.Glide
import java.io.File

class SelfieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelfieBinding

    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnStart.setOnClickListener { startCameraX() }
        binding.btnTryagain.setOnClickListener{ startCameraX()}
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }

    }

    private fun startCameraX() {
        val intent = Intent(this, TakeSelfieActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result =
                rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )
            val resultCrop = result.toSquare()
            getFile = bitmapToFile(resultCrop, application)
            Glide.with(this)
                .load(resultCrop)
                .into(binding.previewImageView)

            binding.apply {
                tvHeader.visibility = View.GONE
                imgPreview.visibility = View.GONE
                btnStart.visibility = View.GONE
                btnNext.visibility = View.VISIBLE
                previewImageView.visibility = View.VISIBLE
                btnTryagain.visibility = View.VISIBLE
            }
        }

    }


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}