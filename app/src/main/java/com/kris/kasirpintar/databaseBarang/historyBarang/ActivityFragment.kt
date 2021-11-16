package com.kris.kasirpintar.databaseBarang.historyBarang

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kris.kasirpintar.R


class ActivityFragment : Fragment() {

    private lateinit var ivHistory: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupListener()
    }

    private fun setupView() {
        ivHistory = requireView().findViewById(R.id.ivHistory)
    }

    private fun setupListener() {

        ivHistory.setOnClickListener {
            startActivity(Intent(requireContext(),HistoryActivity::class.java))
        }

    }

}