package com.bangkit.ocr_c22_ky03.api

import okhttp3.*
import retrofit2.http.*

interface ApiService {

    @POST("")
    fun postKtp(
        @Part ktp: MultipartBody.Part
    )

    @GET("dataktp")
    suspend fun getDataKtp(
    )//: DataResponse
}