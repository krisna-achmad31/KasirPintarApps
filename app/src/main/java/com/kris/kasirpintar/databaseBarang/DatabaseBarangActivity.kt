package com.kris.kasirpintar.databaseBarang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kris.kasirpintar.ApiRetrofit
import com.kris.kasirpintar.databaseBarang.editBarang.BarangAdapter
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.editBarang.addBarang.AddItemActivity
import com.kris.kasirpintar.databaseBarang.editBarang.updateBarang.UpdateItemActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DatabaseBarangActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var barangAdapter: BarangAdapter
    private lateinit var fabAddItem: FloatingActionButton
    private lateinit var listNote: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_barang)
        setupView()
        setupList()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getNote()
    }

    private fun setupView() {
        listNote = findViewById(R.id.rvItem)
        fabAddItem = findViewById(R.id.fabAddItem)

    }

    private fun setupList() {
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnAdapterListener{
            val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
                startActivity(Intent(this@DatabaseBarangActivity,DatabaseBarangActivity::class.java))
            }
            override fun onUpdate(database: BarangModel.Database) {
                startForResult.launch(Intent(this@DatabaseBarangActivity,UpdateItemActivity::class.java)
                    .putExtra("id", database.id)
                    .putExtra("nama_barang", database.nama_barang)
                    .putExtra("kode_barang", database.kode_barang)
                    .putExtra("stok", database.stok))

            }

            override fun onDelete(database: BarangModel.Database) {
                api.delete(database.id).enqueue(object : Callback<BarangModel>{
                    override fun onResponse(call: Call<BarangModel>, response: Response<BarangModel>) {
                        if (response.isSuccessful){
                            Toast.makeText(applicationContext,"Data Berhasil di Delete",Toast.LENGTH_SHORT).show()
                            getNote()
                        }
                    }

                    override fun onFailure(call: Call<BarangModel>, t: Throwable) {

                    }

                })
            }
        })
        listNote.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listNote.adapter = barangAdapter
    }

    private fun setupListener() {
        fabAddItem.setOnClickListener {
            startActivity(Intent(this,AddItemActivity::class.java))
        }

    }

    private fun getNote() {
       api.barangs().enqueue(object : Callback<BarangModel>{
           override fun onResponse(call: Call<BarangModel>, response: Response<BarangModel>) {
               if (response.isSuccessful){
                   val listData = response.body()!!.database
                   barangAdapter.setData(listData)
               }
           }

           override fun onFailure(call: Call<BarangModel>, t: Throwable) {
               Log.e("DatabaseBarangActivity", t.toString())
           }

       })
//        api.barangs().enqueue(object : Callback<NoteModel>{
//            override fun onResponse(call: Call<NoteModel> , response: Response<NoteModel>) {
//                if (response.isSuccessful){
//                    val listData = response.body()!!.barangs
//                    noteAdapter.setData(listData)
////                    Log.e("DatabaseBarangActivity", response.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<NoteModel>, t: Throwable) {
//                Log.e("DatabaseBarangActivity", t.toString())
//            }
//
//        })
    }

}