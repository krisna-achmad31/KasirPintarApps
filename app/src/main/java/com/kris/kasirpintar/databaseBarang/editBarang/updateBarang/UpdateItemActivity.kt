package com.kris.kasirpintar.databaseBarang.editBarang.updateBarang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.kris.kasirpintar.ApiRetrofit
import com.kris.kasirpintar.MainActivity
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var editNamaBarang: EditText
    private lateinit var editKodeBarang: EditText
    private lateinit var editStok: EditText
    private lateinit var buttonUpdate: Button
    private var quantityBarang: Int =0

    private val api by lazy { ApiRetrofit().endpoint}
    private val id by lazy { intent.getIntExtra("id",0) }
    private val nama_barang by lazy { intent.getStringExtra("nama_barang")}
    private val kode_barang by lazy { intent.getStringExtra("kode_barang")}
    private val stok by lazy { intent.getIntExtra("stok",0)}
    private val quantity by lazy { intent.getIntExtra("quantity", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        setupView()
        setupListener()
    }

    private fun setupView() {

        editNamaBarang = findViewById(R.id.etNamaBarang)
        editKodeBarang = findViewById(R.id.etKodeBarang)
        editStok = findViewById(R.id.etStok)

        buttonUpdate = findViewById(R.id.btnUpdate)

//        quantityBarang= stok - quantity

        editNamaBarang.setText(nama_barang)
        editKodeBarang.setText(kode_barang)
        editStok.setText(stok.toString())

    }

    private fun setupListener() {

        buttonUpdate.setOnClickListener {
            api.update(id,editKodeBarang.text.toString(),editNamaBarang.text.toString(),editStok.text.toString().toInt()).enqueue(object : Callback<BarangModel>{
                override fun onResponse(call: Call<BarangModel>, response: Response<BarangModel>) {
                    if (response.isSuccessful){
                        Toast.makeText(applicationContext,"Data Berhasil di Update",Toast.LENGTH_SHORT).show()

                    } else{

                    }
                    startActivity(Intent(this@UpdateItemActivity,MainActivity::class.java))
                    finish()
                }

                override fun onFailure(call: Call<BarangModel>, t: Throwable) {

                }

            })
        }
    }
}