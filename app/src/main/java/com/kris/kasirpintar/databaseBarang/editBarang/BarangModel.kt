package com.kris.kasirpintar.databaseBarang.editBarang

import java.io.Serializable

data class BarangModel(
    val database: List<Database>,
    val message: String
){
    data class Database(
//        @SerializedName("id")
//        @Expose
        val id: Int,

//        @SerializedName("kode_barang")
//        @Expose
        var kode_barang: String?,

//        @SerializedName("nama_barang")
//        @Expose
        var nama_barang: String?,

//        @SerializedName("stok")
//        @Expose
        var stok: Int?,

        val created_at: String,
        val updated_at: String?
    ) : Serializable }



