package com.kris.kasirpintar.databaseBarang.orderBarang

import com.kris.kasirpintar.databaseBarang.editBarang.BarangModel

data class CartItem (var Barang: BarangModel.Database, var quantity: Int = 0)