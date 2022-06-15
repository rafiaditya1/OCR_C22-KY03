package com.bangkit.ocr_c22_ky03.module.ktp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityKtpBinding
import com.bangkit.ocr_c22_ky03.ml.Detect
//import com.bangkit.ocr_c22_ky03.ml.KtpTinyLite416
import com.bangkit.ocr_c22_ky03.utils.rotateBitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class KtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKtpBinding
    private var getFile: File? = null
//    lateinit var result: Bitmap
//    private lateinit var viewModel: UploadViewModel

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

        binding.btnScan.setOnClickListener { startCameraX() }
//        binding.btnTryAgain.setOnClickListener { startCameraX() }
//        binding.btnNext.setOnClickListener {
//            intent = Intent(this@KtpActivity, FormActivity::class.java)
//            intent.putExtra(FormActivity.DATA_KTP, result)
//            startActivity(intent)
//        }




    }

//    private fun getMax(arr: FloatArray): Int {
//
//        var ind = 0
//        var min = 0.0f
//
//        for (i in 0..1) {
//            if (arr[i]>min){
//                ind = i
//                min = arr[i]
//            }
//        }
//        return ind
//    }


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

           val result =
                BitmapFactory.decodeFile(myFile.path)




            binding.ivResult.setImageBitmap(result)
            binding.tvPrepareKTP.visibility = View.INVISIBLE
//            binding.tvMakeSure.visibility = View.INVISIBLE
            binding.ivScan.visibility = View.INVISIBLE
            binding.btnScan.visibility = View.INVISIBLE
            binding.ivResult.visibility = View.VISIBLE
//            binding.linearLayout.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE

//
//
//            val fileName = "labels.txt"
//            val inputString = application.assets.open(fileName).bufferedReader().use { file -> file.readText() }
//            val townList = inputString.split("\n")
//            binding.btnTryAgain.setOnClickListener {
//                val resized: Bitmap = Bitmap.createScaledBitmap(result, 600, 600, true)
//                val model = Detect.newInstance(this)
//                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 300  , 300, 3), DataType.FLOAT32)
//
//                val tBuffer = TensorImage.fromBitmap(resized)
//                val byteBuffer = tBuffer.buffer
//                inputFeature0.loadBuffer(byteBuffer)
//
//                val outputs = model.process(inputFeature0)
//                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//                val max = getMax(outputFeature0.floatArray)
//
//                binding.tvMakeSure.text = townList[max]
//            }
        }

    }


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}