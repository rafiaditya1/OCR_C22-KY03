package com.bangkit.ocr_c22_ky03.api

import com.bangkit.ocr_c22_ky03.module.form.FormResponse
import com.bangkit.ocr_c22_ky03.module.history.*
import com.bangkit.ocr_c22_ky03.module.ktp.UploadKtpResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("ktp")
    fun postKtp(
//        @Part ktp: MultipartBody.Part,
    @Field ("name") name:String,
    @Field ("nik") nik:String
    ): Call<FormResponse>

    @GET("ktp")
    fun getHistory(
    ) : Call<List<DataKtpResponseItem>>

    @Multipart
    @POST("upload/ktp")
    fun postKtp2(
        @Part ktp: MultipartBody.Part,
//        @Field ("name") name:String,
//        @Field ("nik") nik:String
    ): Call<UploadKtpResponse>

    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confPassword") confPassword: String,
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<LoginResponse>

}