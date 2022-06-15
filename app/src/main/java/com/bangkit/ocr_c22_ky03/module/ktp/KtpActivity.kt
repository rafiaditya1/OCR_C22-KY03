package com.bangkit.ocr_c22_ky03.module.ktp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.bangkit.ocr_c22_ky03.ml.KtpTinyLite416
import com.bangkit.ocr_c22_ky03.module.customView.CustomButton
import com.bangkit.ocr_c22_ky03.module.customView.CustomEditText
import com.bangkit.ocr_c22_ky03.utils.ApiCallbackString
import com.bangkit.ocr_c22_ky03.utils.reduceFileImage
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class KtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKtpBinding
    private var getFile: File? = null
    private lateinit var customButton: CustomButton
    private lateinit var customEditText: CustomEditText
    private val viewModel by viewModels<KtpViewModel>()

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


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        customButton = findViewById(R.id.btnScan)
        customEditText = findViewById(R.id.edt_nik)

        setMyButtonEnable()

        customEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.btnScan.setOnClickListener { startCameraX() }
        binding.btnTryAgain.setOnClickListener { startCameraX() }
//        binding.btnTryAgain.setOnClickListener { startCameraX() }
//        binding.btnNext.setOnClickListener {
//            intent = Intent(this@KtpActivity, FormActivity::class.java)
//            intent.putExtra(FormActivity.DATA_KTP, result)
//            startActivity(intent)
        binding.btnNext.setOnClickListener {
//            intent = Intent(this@KtpActivity, FormActivity::class.java)
//            startActivity(intent)
            uploadImage()
//
//        }
        }
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f
        for (i in 0..1000) {
            if (arr[i] > min) {
                ind = i
                min = arr[i]
            }
        }
        return ind
    }

    private fun setMyButtonEnable() {
        val result = customEditText.text
        customButton.isEnabled = result != null && result.toString().length == 16
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
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
//            getFile = bitmapToFile(result, application)

            Glide.with(this)
                .load(result)
                .into(binding.ivResult)



            binding.ivResult.setImageBitmap(result)
            binding.tvPrepareKTP.visibility = View.INVISIBLE
//            binding.tvMakeSure.visibility = View.INVISIBLE
            binding.ivScan.visibility = View.INVISIBLE
            binding.btnScan.visibility = View.INVISIBLE
            binding.ivResult.visibility = View.VISIBLE
//            binding.linearLayout.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE


            val fileName = "labels.txt"
            val inputString =
                application.assets.open(fileName).bufferedReader()
                    .use { file -> file.readText() }
            val townList = inputString.split("\n")
            binding.btnTryAgain.setOnClickListener {
                val resized: Bitmap = Bitmap.createScaledBitmap(result, 416, 416, true)
                val model = KtpTinyLite416.newInstance(this)
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 416, 416, 3), DataType.FLOAT32)

                val tBuffer = TensorImage.fromBitmap(resized)
                val byteBuffer = tBuffer.buffer
                inputFeature0.loadBuffer(byteBuffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                val max = getMax(outputFeature0.floatArray)

                binding.tvMakeSure.text = townList[max]
            }
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

            viewModel.uploadImage(imageMultipart, object : ApiCallbackString {
                override fun onResponse(status: String) {
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