package com.kris.kasirpintar.databaseBarang.orderBarang.firrebaseDatabase

import com.google.firebase.database.ServerValue

class Users(var idBarang: String?, var namaBarang: String?, var kodeBarang: String?, var quantity: String?) {
    constructor() : this("","","",""){
    }
}