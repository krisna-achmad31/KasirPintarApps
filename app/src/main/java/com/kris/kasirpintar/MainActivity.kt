package com.kris.kasirpintar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kris.kasirpintar.databaseBarang.editBarang.EditBarangFragment
import com.kris.kasirpintar.databaseBarang.historyBarang.ActivityFragment
import com.kris.kasirpintar.databaseBarang.orderBarang.OrderBarangFragment
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity()  {

    private val api by lazy { ApiRetrofit().endpoint }
    private val editFragment = EditBarangFragment()
    private val orderBarangFragment = OrderBarangFragment()
    private val activityFragment = ActivityFragment()
//    private lateinit var btnDatabase : Button
    private lateinit var navigation: BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation = findViewById(R.id.containerNavigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, editFragment)
        transaction.addToBackStack(null)
        transaction.commit()

//        btnDatabase = findViewById(R.id.btnDatabase)
//        btnDatabase.setOnClickListener{
//            startActivity(Intent(this, DatabaseBarangActivity::class.java))
//        }

//        api.barang().enqueue(object : Callback<NoteModel>{
//            override fun onResponse(call: Call<NoteModel>, response: Response<NoteModel>) {
//                if (response.isSuccessful){
//                    Log.e("MainActivity", response.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<NoteModel>, t: Throwable) {
//                Log.e("MainActivity",t.toString())
//            }
//
//        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
        item ->
        val transaction =supportFragmentManager.beginTransaction()

        when (item.itemId){
            R.id.idNavigationEdit ->{
                transaction.replace(R.id.containerFragment, editFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.idNavigationShop ->{
                transaction.replace(R.id.containerFragment, orderBarangFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.idNavigationAccount ->{
                transaction.replace(R.id.containerFragment, activityFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}