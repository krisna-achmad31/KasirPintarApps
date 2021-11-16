package com.kris.kasirpintar.databaseBarang.historyBarang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ListView
import com.google.firebase.database.*
import com.kris.kasirpintar.R
import com.kris.kasirpintar.databaseBarang.historyBarang.adapter.HistoryAdapter
import com.kris.kasirpintar.databaseBarang.orderBarang.firrebaseDatabase.Users

class HistoryActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Users>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        ref = FirebaseDatabase.getInstance("https://pretestkasirpintar-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("USERS")
        list = mutableListOf()
        listView = findViewById(R.id.lvHistory)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    for (h in p0.children){
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                    val adapter = HistoryAdapter(applicationContext,R.layout.layout_history_list,list)
                    listView.adapter = adapter
                }
            }
        })
    }

}