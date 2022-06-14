package com.bangkit.ocr_c22_ky03.api

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("status")
	val status: String
)
