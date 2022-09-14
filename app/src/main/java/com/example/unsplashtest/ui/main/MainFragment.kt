package com.example.unsplashtest.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashtest.PhotoAdapter
import com.example.unsplashtest.R
import com.example.unsplashtest.service.UnsplashService
import com.google.gson.JsonElement

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        UnsplashService.Companion.unsplashServiceInstance.getLinks(object : UnsplashService.LinksListListener {
            override fun onLinksReceived(list: List<JsonElement>) {
                val rvContacts = view!!.findViewById<View>(R.id.recycler_photos) as RecyclerView
                val adapter = PhotoAdapter(list)
                rvContacts.adapter = adapter

                rvContacts.layoutManager = LinearLayoutManager(context)
            }

        })
    }

}