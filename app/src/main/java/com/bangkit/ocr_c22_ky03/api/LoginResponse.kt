package com.bangkit.ocr_c22_ky03.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null
)
