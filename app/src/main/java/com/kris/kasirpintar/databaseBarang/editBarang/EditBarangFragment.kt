package com.kris.kasirpintar.databaseBarang.editBarang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kris.kasirpintar.*
import com.kris.kasirpintar.databaseBarang.editBarang.addBarang.AddItemActivity
import com.kris.kasirpintar.databaseBarang.editBarang.updateBarang.UpdateItemActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditBarangFragment : Fragment() {

    private val api by lazy { ApiRetrofit().endpoint }

    private lateinit var viewOfLayout: View
    private lateinit var barangAdapter: BarangAdapter
    private lateinit var fabAddItem: FloatingActionButton
    private lateinit var listNote: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit, container, false)


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

//        listNote = rvItem.apply {
//            layoutManager = LinearLayoutManager(activity)
//        }
//        listNote = viewOfLayout(R.id.rvItem)
        listNote = requireView().findViewById(R.id.rvItem)
        fabAddItem = requireView().findViewById(R.id.fabAddItem)

    }

    private fun setupList() {
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnAdapterListener{
            val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
                startActivity(
                    Intent(requireContext(),
                        MainActivity::class.java)
                )
            }
            override fun onUpdate(database: BarangModel.Database) {
                startForResult.launch(
                    Intent(requireContext(), UpdateItemActivity::class.java)
                    .putExtra("id", database.id)
                    .putExtra("nama_barang", database.nama_barang)
                    .putExtra("kode_barang", database.kode_barang)
                    .putExtra("stok", database.stok))

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
        fabAddItem.setOnClickListener {
            startActivity(Intent(requireContext(), AddItemActivity::class.java))
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