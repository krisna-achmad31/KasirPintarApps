package com.kris.kasirpintar.databaseBarang.orderBarang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.databaseBarang.orderBarang.CartItem
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.layout_adapter_barang_cart.view.*

class CartAdapter(var context: Context, var cartItem: ArrayList<CartItem>, val listener: OnAdapterListenerStok): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = cartItem[position]
        holder.namaBarangCart?.text = order.Barang.nama_barang
        holder.kodebarangCart?.text = order.Barang.kode_barang
        holder.stokBarangCart?.text = order.Barang.stok.toString()

        holder.bindItem(cartItem[position])

        holder.checkBarangCart.setOnClickListener {
            listener.newStock(order)
        }
    }

    override fun getItemCount(): Int = cartItem.size




    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val namaBarangCart: TextView? = view.findViewById(R.id.tvNamaBarangCartItem)
        val kodebarangCart: TextView? = view.findViewById(R.id.tvKodeBarangCartItem)
        val stokBarangCart: TextView? = view.findViewById(R.id.tvJumlahStokCartItem)
        val checkBarangCart: CheckBox = view.findViewById(R.id.cbBarangCartItem)

        fun bindItem(cartItem: CartItem){


            itemView.tvNamaBarangCartItem?.text = cartItem.Barang.nama_barang
            itemView.tvKodeBarangCartItem?.text = cartItem.Barang.kode_barang
            itemView.tvJumlahStokCartItem?.text = cartItem.Barang.stok.toString()

            itemView.tvQuantityCartItem.text = cartItem.quantity.toString()
        }
    }



    interface OnAdapterListenerStok{
        fun newStock(newStock: CartItem)
    }



}
