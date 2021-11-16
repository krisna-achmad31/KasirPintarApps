package com.kris.kasirpintar.databaseBarang.historyBarang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.orderBarang.firrebaseDatabase.Users

class HistoryAdapter (val mCtx: Context, val layoutResId: Int, val list: List<Users> )
    : ArrayAdapter<Users>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val namaBarangHistory = view.findViewById<TextView>(R.id.tvNamaBarangHistoryItem)
        val kodeBarangHistory = view.findViewById<TextView>(R.id.tvKodeBarangHistoryItem)
        val quantityBarangHistory = view.findViewById<TextView>(R.id.tvQuantityHistoryItem)
//        val waktuBarangHistory = view.findViewById<TextView>(R.id.tvTimeHistoryItem)

        val user = list[position]

        namaBarangHistory.text = user.namaBarang
        kodeBarangHistory.text = user.kodeBarang
        quantityBarangHistory.text = user.quantity
//        waktuBarangHistory.text = user.time

        return view

    }
}