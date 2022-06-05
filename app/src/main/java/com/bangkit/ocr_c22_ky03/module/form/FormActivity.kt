package com.bangkit.ocr_c22_ky03.module.form

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.ocr_c22_ky03.databinding.ActivityFormBinding
import com.bangkit.ocr_c22_ky03.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnFinish.setOnClickListener{
//            intent = Intent(this@FormActivity, SelfieActivity::class.java)
//            startActivity(intent)
//        }

        val dataKtp = MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.parse(DATA_KTP))
        val fileName = "labels.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        val townList = inputString.split("\n")

        binding.btnPredict.setOnClickListener{
            val resized: Bitmap = Bitmap.createScaledBitmap(dataKtp, 224, 224, true)

            val model = MobilenetV110224Quant.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)

            val tBuffer = TensorImage.fromBitmap(resized)
            val byteBuffer = tBuffer.buffer
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val max = getMax(outputFeature0.floatArray)

            binding.tvHasil.text = townList[max]

            model.close()
        }


    }

    fun getMax(arr: FloatArray): Int {

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