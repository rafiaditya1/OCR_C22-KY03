package com.bangkit.ocr_c22_ky03.module.form

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ktp2Response(

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("kwn")
	val kwn: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("agama")
	val agama: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("ttl")
	val ttl: String? = null,

	@field:SerializedName("alamat")
	val alamat: List<Alamat>? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Alamat(

	@field:SerializedName("kel")
	val kel: String? = null,

	@field:SerializedName("kec")
	val kec: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
) : Parcelable
