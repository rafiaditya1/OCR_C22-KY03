package com.bangkit.ocr_c22_ky03.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("userId")
	val id: Int,

	@field:SerializedName("accessToken")
	val accessToken: String? = null
)
