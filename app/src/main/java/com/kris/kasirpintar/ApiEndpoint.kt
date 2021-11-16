package com.kris.kasirpintar

import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.databaseBarang.editBarang.addBarang.AddModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("api/barang")
    fun barangs() : Call<BarangModel>

    @FormUrlEncoded
    @POST("api/barang")
    fun add(
        @Field("kode_barang") kode_barang: String,
        @Field("nama_barang") nama_barang: String,
        @Field("stok") stok: Int
    ) : Call<AddModel>

    @FormUrlEncoded
    @PUT("api/barang/{id}")
    fun update(
        @Path("id") id: Int?,
        @Field("kode_barang") kode_barang: String,
        @Field("nama_barang") nama_barang: String,
        @Field("stok") stok: Int
    ) : Call<BarangModel>


    @DELETE("api/barang/{id}")
    fun delete(
        @Path("id") id: Int?
    ) : Call<BarangModel>
}