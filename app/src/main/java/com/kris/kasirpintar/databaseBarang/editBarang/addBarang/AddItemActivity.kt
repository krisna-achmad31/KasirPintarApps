package com.kris.kasirpintar.databaseBarang.editBarang.addBarang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.kris.kasirpintar.ApiRetrofit
import com.kris.kasirpintar.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddItemActivity : AppCompatActivity() {

    private lateinit var editNamaBarang: EditText
    private lateinit var editKodeBarang: EditText
    private lateinit var editStok: EditText
    private lateinit var buttonAdd: Button

    private val api by lazy { ApiRetrofit().endpoint}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        supportActionBar!!.title = "Menambah Barang"

        setupView()
        setupListener()
    }

    private fun setupView() {

        editNamaBarang = findViewById(R.id.etNamaBarang)
        editKodeBarang = findViewById(R.id.etKodeBarang)
        editStok = findViewById(R.id.etStok)

        buttonAdd = findViewById(R.id.btnAdd)

    }

    private fun setupListener() {

        buttonAdd.setOnClickListener {
            if (editNamaBarang.text.toString().isNotEmpty()&&editKodeBarang.text.toString().isNotEmpty()&&editStok.text.toString().isNotEmpty()){
                Log.e("AddItemActivity", editNamaBarang.text.toString())
                api.add(editKodeBarang.text.toString(),editNamaBarang.text.toString(),editStok.text.toString().toInt())
                    .enqueue(object : Callback<AddModel> {
                        override fun onResponse(
                            call: Call<AddModel>,
                            response: Response<AddModel>
                        ) {
                            if (response.isSuccessful){
                                val add = response.body()
                                if (add != null) {
                                    Toast.makeText(applicationContext, add.result, Toast.LENGTH_SHORT)
                                        .show()
                                    finish()
                                }
                            }
                        }

                        override fun onFailure(call: Call<AddModel>, t: Throwable) {

                        }

                    })
                Log.e("AddItemActivity", editKodeBarang.text.toString())
                Log.e("AddItemActivity", editStok.text.toString())
            } else{
                Toast.makeText(applicationContext,"Data Harus Diisi Semua", Toast.LENGTH_SHORT).show()
            }

        }
    }
}