package com.kris.kasirpintar.databaseBarang.orderBarang.updateStok

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import com.kris.kasirpintar.ApiRetrofit
import com.kris.kasirpintar.MainActivity
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.databaseBarang.orderBarang.firrebaseDatabase.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateStokActivity : AppCompatActivity() {

    private lateinit var editNamaBarang: EditText
    private lateinit var editKodeBarang: EditText
    private lateinit var editStok: EditText
    private lateinit var editWaktu: EditText
    private lateinit var buttonUpdate: Button

    private var stokUpdate: Int = 0
    private var quantityBarang: Int =0

    private val api by lazy { ApiRetrofit().endpoint}
    private val id by lazy { intent.getIntExtra("id",0) }
    private val nama_barang by lazy { intent.getStringExtra("nama_barang")}
    private val kode_barang by lazy { intent.getStringExtra("kode_barang")}
    private val stok by lazy { intent.getIntExtra("stok",0)}
//    private val waktu by lazy { intent.getStringExtra("updated_at") }
    private val quantity by lazy { intent.getIntExtra("quantity", 0) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_stok)

        setupView()
        setupListener()


    }


    private fun setupView() {
        editNamaBarang = findViewById(R.id.etNamaBarang)
        editKodeBarang = findViewById(R.id.etKodeBarang)
        editStok = findViewById(R.id.etStok)

        buttonUpdate = findViewById(R.id.btnUpdate)

        stokUpdate= stok - quantity

//        quantityBarang= stok - quantity

        editNamaBarang.setText(nama_barang)
        editKodeBarang.setText(kode_barang)
        editStok.setText(stokUpdate.toString())
    }

    private fun setupListener() {
        api.update(id,editKodeBarang.text.toString(),editNamaBarang.text.toString(),editStok.text.toString().toInt()).enqueue(object :
            Callback<BarangModel> {
            override fun onResponse(call: Call<BarangModel>, response: Response<BarangModel>) {
                if (response.isSuccessful){
                    Toast.makeText(applicationContext,"Barang Berhasil dipilih", Toast.LENGTH_SHORT).show()
                    saveData()
                } else{

                }

                finish()
            }

            override fun onFailure(call: Call<BarangModel>, t: Throwable) {

            }

        })


    }

    private fun saveData() {
        editNamaBarang = findViewById(R.id.etNamaBarang)
        editKodeBarang = findViewById(R.id.etKodeBarang)
        editStok = findViewById(R.id.etStok)


        val ref: DatabaseReference = FirebaseDatabase.getInstance("https://pretestkasirpintar-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("USERS")

        val idBarang = id
        val namaBarang =nama_barang
        val kodeBarang=kode_barang
//        val waktuBarang=waktu

        val quantity=quantity

        val user = Users(idBarang.toString(),namaBarang,kodeBarang,quantity.toString())
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            editNamaBarang.setText("")
            editKodeBarang.setText("")
            editStok.setText("")
        }
    }
}