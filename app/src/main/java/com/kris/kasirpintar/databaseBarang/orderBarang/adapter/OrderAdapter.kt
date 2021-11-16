package com.kris.kasirpintar.databaseBarang.orderBarang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.orderBarang.CartItem
import com.kris.kasirpintar.databaseBarang.orderBarang.OrderBarangFragment
import com.kris.kasirpintar.databaseBarang.orderBarang.OrderCart
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.fragment_order_barang.*
import kotlinx.android.synthetic.main.layout_adapter_barang_cart.view.*

class OrderAdapter(
    var products: ArrayList<BarangModel.Database>,
    val listener: OnAdapterListener
//    var context: Context, var products: ArrayList<BarangModel.Database>


): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_adapter_barang_cart, parent, false)



    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = products[position]
        holder.namaBarangCart?.text = order.nama_barang
        holder.kodebarangCart?.text = order.kode_barang
        holder.stokBarangCart?.text = order.stok.toString()

        holder.itemView.setOnClickListener {
            listener.onUpdate( order )
        }

        holder.bindProduct(products[position])




    }

    override fun getItemCount() = products.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val namaBarangCart: TextView? = view.findViewById(R.id.tvNamaBarangCart)
        val kodebarangCart: TextView? = view.findViewById(R.id.tvKodeBarangcart)
        val stokBarangCart: TextView? = view.findViewById(R.id.tvJumlahStokCart)

        val addcart: ImageView? = view.findViewById(R.id.ivAddCart)
        val removeCart: ImageView? = view.findViewById(R.id.ivRemoveCart)


        @SuppressLint("CheckResult")
        fun bindProduct(BarangModel: BarangModel.Database){

            itemView.tvNamaBarangCart.text = BarangModel.nama_barang
            itemView.tvKodeBarangcart.text = BarangModel.kode_barang
            itemView.tvJumlahStokCart.text = BarangModel.stok.toString()

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                itemView.ivAddCart.setOnClickListener { view->
                    val item = CartItem(BarangModel)

                    OrderCart.addItem(item)
                    Snackbar.make(
                        (itemView.context as OrderBarangFragment).containerOrder,"${BarangModel.nama_barang} added to your cart",Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(OrderCart.getCart())
                }

                itemView.ivRemoveCart.setOnClickListener { view->
                    val item = CartItem(BarangModel)

                    OrderCart.removeItem(item, itemView.context)
                    Snackbar.make(
                        (itemView.context as OrderBarangFragment).containerOrder,"${BarangModel.nama_barang} removed from your cart",Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(OrderCart.getCart())
                }

            }).subscribe{cart->
                var quantity = 0

                cart.forEach { cartItem ->

                    quantity += cartItem.quantity
                }

                (itemView.context as OrderBarangFragment).tvCartSize.text =quantity.toString()
                Toast.makeText(itemView.context, " Cart size $quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setData(data: List<BarangModel.Database>){
        products.clear()
        products.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onUpdate(database: BarangModel.Database)

        fun onDelete(database: BarangModel.Database)
    }


}