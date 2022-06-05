package com.bangkit.ocr_c22_ky03.module.ktp

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.ocr_c22_ky03.module.form.FormActivity
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityKtpBinding
import com.bangkit.ocr_c22_ky03.ml.KtpTinyLite416
import com.bangkit.ocr_c22_ky03.utils.rotateBitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
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

    private fun getMax(arr: FloatArray): Int {

        var ind = 0
        var min = 0.0f

        for (i in 0..1000) {
            if (arr[i]>min){
                ind = i
                min = arr[i]
            }
        }
        return ind
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

           val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )




            binding.ivResult.setImageBitmap(result)
            binding.tvPrepareKTP.visibility = View.INVISIBLE
//            binding.tvMakeSure.visibility = View.INVISIBLE
            binding.ivScan.visibility = View.INVISIBLE
            binding.btnScan.visibility = View.INVISIBLE
            binding.ivResult.visibility = View.VISIBLE
//            binding.linearLayout.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE


            binding.btnTryAgain.setOnClickListener {
                val resized: Bitmap = Bitmap.createScaledBitmap(result, 416, 416, true)
//                val model = KtpTinyLite416.newInstance(this)
//                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 416  , 416, 3), DataType.FLOAT32)
//
//                val tBuffer = TensorImage.fromBitmap(resized)
//                val byteBuffer = tBuffer.buffer
//                inputFeature0.loadBuffer(byteBuffer)
//
//                val outputs = model.process(inputFeature0)
//                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//                val max = getMax(outputFeature0.floatArray)

                binding.tvMakeSure.text = runObjectDetection(resized).toString()
            }
        }

    }

    private fun runObjectDetection(bitmap: Bitmap) {
        val image = TensorImage.fromBitmap(bitmap)
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.5f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            this, // the application context
            "ktp-tiny-lite-416.tflite", // must be same as the filename in assets folder
            options
        )
        val results = detector.detect(image)
        debugPrint(results)
    }

    private fun debugPrint(results : List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(TAG, "Detected object: ${i} ")
            Log.d(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(TAG, "    Confidence: ${confidence}%")
            }
        }
    }





    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}