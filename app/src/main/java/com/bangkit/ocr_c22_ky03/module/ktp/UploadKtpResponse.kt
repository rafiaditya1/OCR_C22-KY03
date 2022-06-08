package com.bangkit.ocr_c22_ky03.module.ktp

import com.google.gson.annotations.SerializedName

data class UploadKtpResponse(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
