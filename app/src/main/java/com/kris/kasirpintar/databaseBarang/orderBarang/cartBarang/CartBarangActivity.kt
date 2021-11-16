package com.kris.kasirpintar.databaseBarang.orderBarang.cartBarang

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kris.kasirpintar.ApiRetrofit
import com.kris.kasirpintar.MainActivity
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.databaseBarang.editBarang.updateBarang.UpdateItemActivity
import com.kris.kasirpintar.databaseBarang.orderBarang.CartItem
import com.kris.kasirpintar.databaseBarang.orderBarang.OrderCart
import com.kris.kasirpintar.databaseBarang.orderBarang.adapter.CartAdapter
import com.kris.kasirpintar.databaseBarang.orderBarang.updateStok.UpdateStokActivity
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartBarangActivity : AppCompatActivity() {

    lateinit var adapter: CartAdapter
    private lateinit var listBarang: RecyclerView
    private lateinit var btnCheckout: Button
    private lateinit var tvCartNamaBarang: TextView
    private lateinit var tvCartKodeBarang: TextView
    private lateinit var tvCartStokBarang: TextView
    private var stokUpdate: Int = 0

    private val api by lazy { ApiRetrofit().endpoint}
    private val id by lazy { intent.getIntExtra("id",0) }
//    private val nama_barang by lazy { intent.getStringExtra("nama_barang")}
//    private val kode_barang by lazy { intent.getStringExtra("kode_barang")}
//    private val stok by lazy { intent.getIntExtra("stok",0)}
//    private val quantity by lazy { intent.getIntExtra("quantity",0) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_barang)


//        stokUpdate= stok - quantity
//
//        tvCartNamaBarang.setText(nama_barang)
//        tvCartKodeBarang.setText(kode_barang)
//        tvCartStokBarang.setText(stok.toString())

        adapter = CartAdapter(this, OrderCart.getCart() as ArrayList<CartItem>, object: CartAdapter.OnAdapterListenerStok{
            override fun newStock(newStock: CartItem) {
                startActivity(Intent(this@CartBarangActivity,UpdateStokActivity::class.java)
                    .putExtra("quantity",newStock.quantity)
                    .putExtra("id",newStock.Barang.id)
                    .putExtra("nama_barang",newStock.Barang.nama_barang)
//                    .putExtra("updated_at",newStock.Barang.updated_at)
                    .putExtra("kode_barang",newStock.Barang.kode_barang)
                    .putExtra("stok",newStock.Barang.stok)
                )
            }

        })
        adapter.notifyDataSetChanged()

        btnCheckout= findViewById(R.id.btnCheckOut)

        btnCheckout.setOnClickListener {
            OrderCart.clearItem()
            startActivity(Intent(this,MainActivity::class.java))
        }


        listBarang = findViewById(R.id.rvCartBarang)

        listBarang.adapter = adapter
        listBarang.layoutManager = LinearLayoutManager(this)

        btnCheckout= findViewById(R.id.btnCheckOut)

        tvCartNamaBarang= findViewById(R.id.tvCartNamaBarang)
        tvCartKodeBarang= findViewById(R.id.tvCartKodeBarang)
        tvCartStokBarang= findViewById(R.id.tvCartStokBarang)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId){
            android.R.id.home ->{
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun getQuantity(quantity: CartItem){
        Intent(this,UpdateItemActivity::class.java)
            .putExtra("quantity", quantity.toString())

    }

}