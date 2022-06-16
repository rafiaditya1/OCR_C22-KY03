package com.bangkit.ocr_c22_ky03.module.ktp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadKtpResponse(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
