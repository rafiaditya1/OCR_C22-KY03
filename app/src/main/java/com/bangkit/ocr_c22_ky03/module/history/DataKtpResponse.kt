package com.bangkit.ocr_c22_ky03.module.history

import com.google.gson.annotations.SerializedName

data class DataKtpResponse(

	@field:SerializedName("DataKtpResponse")
	val items: List<DataKtpResponseItem?>? = null
)

data class DataKtpResponseItem(

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("kode_pos")
	val kodePos: String? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("kewarganegaraan")
	val kewarganegaraan: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("tempat")
	val tempat: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("gol_darah")
	val golDarah: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("status_perkawinan")
	val statusPerkawinan: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
