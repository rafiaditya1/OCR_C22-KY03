package com.bangkit.ocr_c22_ky03.module.ktp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityKtpBinding
import com.bangkit.ocr_c22_ky03.module.authentication.UserPreference
import com.bangkit.ocr_c22_ky03.module.customView.CustomButton
import com.bangkit.ocr_c22_ky03.utils.UploadCallbackString
import com.bangkit.ocr_c22_ky03.utils.reduceFileImage
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class KtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKtpBinding
    private var getFile: File? = null
    private lateinit var customButton: CustomButton
    private val viewModel by viewModels<KtpViewModel>()
    private lateinit var userPreference: UserPreference

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityKtpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        userPreference = UserPreference(this)


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        customButton = findViewById(R.id.btnScan)

        binding.btnScan.setOnClickListener { startCameraX() }
        binding.btnTryAgain.setOnClickListener { startCameraX() }
//        binding.btnTryAgain.setOnClickListener { startCameraX() }
//        binding.btnNext.setOnClickListener {
//            intent = Intent(this@KtpActivity, FormActivity::class.java)
//            intent.putExtra(FormActivity.DATA_KTP, result)
//            startActivity(intent)
        binding.btnNext.setOnClickListener {
            uploadImage()
            viewModel.link.observe(this){
                userPreference.preference.edit().putString("path", it.name_file).apply()
            }

        }


    }

    private fun startCameraX() {
        val intent = Intent(this, ScanActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File

            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)

            Glide.with(this)
                .load(result)
                .into(binding.ivResult)

            binding.ivResult.setImageBitmap(result)
            binding.tvPrepareKTP.visibility = View.INVISIBLE
            binding.ivScan.visibility = View.INVISIBLE
            binding.btnScan.visibility = View.INVISIBLE
            binding.ivResult.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "ktp",
                file.name,
                requestImageFile
            )
            viewModel.uploadImage(imageMultipart, object : UploadCallbackString {
                override fun onResponse(status: String, path: String) {
                    if (status == "success") {
                        val a = true
                        showAlertDialog(a, status)
                    } else {
                        val a = false
                        showAlertDialog(a, status)
                    }
                }
            })
        } else {
//            showToast(this@AddStoryActivity, getString(R.string.error_file))
        }
    }

    private fun showAlertDialog(param: Boolean, status: String) {
        if (param) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information_title))
                setMessage(getString(R.string.upload_success))
                setPositiveButton(getString(R.string.btn_continue)) { _, _ ->
                    viewModel.postPath(userPreference)
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

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}