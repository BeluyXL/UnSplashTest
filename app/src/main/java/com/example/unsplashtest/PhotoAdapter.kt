package com.example.unsplashtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashtest.ui.main.SecondFragment
import com.google.gson.JsonElement
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PhotoAdapter (private val mPhotos: List<JsonElement>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text_name = itemView.findViewById<TextView>(R.id.text_name)
        val text_author = itemView.findViewById<TextView>(R.id.text_author)
        val image = itemView.findViewById<ImageView>(R.id.image)
        val history_constraint = itemView.findViewById<ConstraintLayout>(R.id.photo_constraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.item_photo, parent, false)
        return ViewHolder(contactView)
    }



    override fun onBindViewHolder(viewHolder: PhotoAdapter.ViewHolder, position: Int) {
        val photoObject = mPhotos.get(position).asJsonObject
        viewHolder.text_author.text = photoObject.getAsJsonObject("user").get("name").asString
        viewHolder.text_name.text = photoObject.get("id").asString
        Picasso.get().load(photoObject.getAsJsonObject("links").get("download").asString).into(viewHolder.image)




        viewHolder.history_constraint.setOnClickListener {
                        val bundle = Bundle()
            bundle.putString("link", photoObject.getAsJsonObject("links").get("download").asString)
            val secondFragment = SecondFragment()
            secondFragment.arguments = bundle
            (viewHolder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.container, secondFragment).addToBackStack(null).commit()
        }



    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }
}