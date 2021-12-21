package com.tetsoft.staticmapsapi.geocoder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tetsoft.staticmapsapi.databinding.GeoObjectItemBinding
import com.tetsoft.staticmapsapi.geocoder.metadata.FeatureMember

class GeoObjectAdapter(private val geoObjectList : List<FeatureMember>) : RecyclerView.Adapter<GeoObjectAdapter.GeoObjectViewHolder>() {

    inner class GeoObjectViewHolder(binding : GeoObjectItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val location = binding.tvLocationName
        val description = binding.tvDescription
        val position = binding.tvCoordinates
        val buttonWatch : Button = binding.buttonWatch
        val mapsImage : ImageView = binding.mapsImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeoObjectViewHolder {
        return GeoObjectViewHolder(
            GeoObjectItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: GeoObjectViewHolder, position: Int) {
        val item = geoObjectList[position]
        holder.location.text = item.GeoObject.name
        holder.description.text = item.GeoObject.description
        holder.position.text = item.GeoObject.Point.pos
        holder.buttonWatch.setOnClickListener {
            val positionRaw = geoObjectList.get(position).GeoObject.Point.pos
            val positionParsed = positionRaw.replace(" ", ",")
            Picasso.get()
                .load("https://static-maps.yandex.ru/1.x/?ll=${positionParsed}&z=18&l=map&pt=${positionParsed},pm2bll")
                .into(holder.mapsImage)
        }
    }

    override fun getItemCount(): Int {
        return geoObjectList.size
    }

}