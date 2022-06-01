package com.bangkit.ocr_c22_ky03.api

import com.bangkit.ocr_c22_ky03.module.form.FormResponse
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponse
import com.bangkit.ocr_c22_ky03.module.history.DataKtpResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("dataktp")
    fun postKtp(
//        @Part ktp: MultipartBody.Part,
    @Field ("name") name:String,
    @Field ("nik") nik:String
    ): Call<FormResponse>


    @GET("dataktp")
    fun getHistory(
    ) : Call<List<DataKtpResponseItem>>
}