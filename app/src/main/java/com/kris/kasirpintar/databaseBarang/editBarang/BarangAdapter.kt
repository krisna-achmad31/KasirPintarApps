package com.kris.kasirpintar.databaseBarang.editBarang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kris.kasirpintar.R

class BarangAdapter(

    private var notes: ArrayList<BarangModel.Database>,
    val listener: OnAdapterListener

    ): RecyclerView.Adapter<BarangAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_adapter_barang, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = notes[position]
        holder.namaBarang?.text = data.nama_barang
        holder.kodeBarang?.text = data.kode_barang
        holder.stokBarang?.text = data.stok.toString()


        holder.itemView.setOnClickListener {
            listener.onUpdate( data )
        }

        holder.imageDelete?.setOnClickListener {
            listener.onDelete( data)
        }

    }

    override fun getItemCount() = notes.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val namaBarang: TextView? = view.findViewById(R.id.tvNamaBarang)
        val kodeBarang: TextView? = view.findViewById(R.id.tvKodeBarang)
        val stokBarang: TextView? = view.findViewById(R.id.tvJumlahStok)

        val imageDelete: ImageView? = view.findViewById(R.id.ivDelete)


    }

    fun setData(data: List<BarangModel.Database>){
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(database: BarangModel.Database)
        fun onDelete(database: BarangModel.Database)
    }

}