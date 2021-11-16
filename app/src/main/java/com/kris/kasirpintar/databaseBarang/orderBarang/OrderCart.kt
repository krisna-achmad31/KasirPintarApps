package com.kris.kasirpintar.databaseBarang.orderBarang

import android.content.Context
import android.widget.Toast
import io.paperdb.Paper

class OrderCart {

    companion object{

        fun addItem(cartItem: CartItem){
            val cart = OrderCart.getCart()

            val targetItem = cart.singleOrNull { it.Barang.id == cartItem.Barang.id }

            if (targetItem == null){
                cartItem.quantity++
                cart.add(cartItem)
            } else{

                targetItem.quantity++
            }
            OrderCart.saveCart(cart)

        }

        fun removeItem(cartItem: CartItem, context: Context){
            val cart =OrderCart.getCart()

            val targetItem = cart.singleOrNull { it.Barang.id == cartItem.Barang.id }

            if (targetItem != null) {
                if (targetItem.quantity > 0) {

                    Toast.makeText(context, "great quantity", Toast.LENGTH_SHORT).show()
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }
            OrderCart.saveCart(cart)
        }

        fun clearItem(){
            return Paper.book().destroy()
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {

            var cartSize = 0
            OrderCart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }
}