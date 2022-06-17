package com.bangkit.ocr_c22_ky03.utils

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.view.View
import com.bangkit.ocr_c22_ky03.R
import java.io.*
import java.nio.file.Files.createFile
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min


interface ApiCallbackString {
    fun onResponse(msg: String)
}
interface AuthCallbackString {
    fun onResponse(status: String, msg: String)
}

interface LoginCallbackString {
    fun onResponse(msg: String,)
}

interface UploadCallbackString {
    fun onResponse(msg: String, path: String)
}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun kropBitmap(bitmap: Bitmap): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(90f)
    return Bitmap.createBitmap(
        bitmap,
        0,
        0,
        bitmap.width,
        bitmap.height,
        matrix,
        true
    )

}

fun Bitmap.toSquare():Bitmap?{
    // get the small side of bitmap
    val side = min(width,height)

    // calculate the x and y offset
    val xOffset = (width - side) /2
    val yOffset = (height - side)/2

    // create a square bitmap
    // a square is closed, two dimensional shape with 4 equal sides
    return Bitmap.createBitmap(
        this, // source bitmap
        xOffset, // x coordinate of the first pixel in source
        yOffset, // y coordinate of the first pixel in source
        side, // width
        side // height
    )
}


fun Bitmap.toSelfie(bitmap:Bitmap?):Bitmap?{
    val squareBitmapWidth = min(width,height)
    // Initialize a new instance of Bitmap
    // Initialize a new instance of Bitmap
    val dstBitmap = Bitmap.createBitmap(
        squareBitmapWidth,  // Width
        squareBitmapWidth,  // Height
        Bitmap.Config.ARGB_8888 // Config
    )
    val canvas = Canvas(dstBitmap)
    // Initialize a new Paint instance
    // Initialize a new Paint instance
    val paint = Paint()
    paint.isAntiAlias = true
    val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
    val rectF = RectF(rect)
    canvas.drawOval(rectF, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    // Calculate the left and top of copied bitmap
    // Calculate the left and top of copied bitmap
    val left = ((squareBitmapWidth - width) / 2).toFloat()
    val top = ((squareBitmapWidth - height) / 2).toFloat()
    bitmap?.let { canvas.drawBitmap(it, left, top, paint) }
    // Free the native object associated with this bitmap.
    // Free the native object associated with this bitmap.
    recycle()
    // Return the circular bitmap
    // Return the circular bitmap
    return dstBitmap
}

fun bitmapToFile(bitmap: Bitmap?, application: Application): File {
    val photoFile = createFile(application)
    val bmpStream = ByteArrayOutputStream()

    bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, bmpStream)
    val bmpPicByteArray = bmpStream.toByteArray()

    val fos = FileOutputStream(photoFile)
    fos.write(bmpPicByteArray)
    fos.flush()
    fos.close()

    return photoFile
}

fun showLoading(isLoading: Boolean, view: View) {
    if (isLoading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}
fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)

    var compressQuality = 80
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

    return file
}

