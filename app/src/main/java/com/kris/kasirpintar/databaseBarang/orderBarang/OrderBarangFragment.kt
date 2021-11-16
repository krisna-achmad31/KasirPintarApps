package com.kris.kasirpintar.databaseBarang.orderBarang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kris.kasirpintar.*
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.databaseBarang.editBarang.updateBarang.UpdateItemActivity
import com.kris.kasirpintar.databaseBarang.orderBarang.cartBarang.CartBarangActivity
import com.kris.kasirpintar.databaseBarang.orderBarang.adapter.OrderAdapter
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderBarangFragment : Fragment() {

    private val api by lazy { ApiRetrofit().endpoint }

    private lateinit var viewOfLayout: View
    private lateinit var barangAdapter: OrderAdapter
    private lateinit var fabCartItem: FloatingActionButton
    private lateinit var listNote: RecyclerView
    private lateinit var cartSize: TextView

    private var products = ArrayList<BarangModel.Database>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Paper.init(requireContext())

        return inflater.inflate(R.layout.fragment_order_barang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupList()
        setupListener()
    }


    override fun onStart() {
        super.onStart()
        getNote()
    }

    private fun setupView() {

        listNote = requireView().findViewById(R.id.rvItemCart)
        fabCartItem = requireView().findViewById(R.id.fabCartItem)
        cartSize = requireView().findViewById(R.id.tvCartSize)

        cartSize.text =OrderCart.getShoppingCartSize().toString()

    }

    private fun setupList() {

//        barangAdapter = OrderAdapter(requireContext(), products)
        barangAdapter = OrderAdapter(arrayListOf(), object : OrderAdapter.OnAdapterListener{
            val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
                startActivity(
                    Intent(requireContext(),
                        OrderBarangFragment::class.java)
                )
            }
            override fun onUpdate(database: BarangModel.Database) {
                Intent(requireContext(), UpdateItemActivity::class.java)
                        .putExtra("id", database.id)
                        .putExtra("nama_barang", database.nama_barang)
                        .putExtra("updated_at", database.updated_at)
                        .putExtra("kode_barang", database.kode_barang)
                        .putExtra("stok", database.stok)
//                startForResult.launch(
//                    )

            }

            override fun onDelete(database: BarangModel.Database) {
                api.delete(database.id).enqueue(object : Callback<BarangModel> {
                    override fun onResponse(call: Call<BarangModel>, response: Response<BarangModel>) {
                        if (response.isSuccessful){
                            Toast.makeText(requireContext(),"Data Berhasil di Delete", Toast.LENGTH_SHORT).show()
                            getNote()
                        }
                    }

                    override fun onFailure(call: Call<BarangModel>, t: Throwable) {

                    }

                })
            }
        })
        listNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listNote.adapter = barangAdapter
    }

    private fun setupListener() {

        fabCartItem.setOnClickListener {
            startActivity(Intent(requireContext(), CartBarangActivity::class.java))
        }
    }

    private fun getNote() {
        api.barangs().enqueue(object : Callback<BarangModel> {
            override fun onResponse(call: Call<BarangModel>, response: Response<BarangModel>) {
                if (response.isSuccessful) {
                    val listData = response.body()!!.database
                    barangAdapter.setData(listData)
                }
            }

            override fun onFailure(call: Call<BarangModel>, t: Throwable) {
                Log.e("DatabaseBarangActivity", t.toString())
            }

        })

    }


}