package com.tetsoft.staticmapsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.tetsoft.staticmapsapi.databinding.ActivityMainBinding
import com.tetsoft.staticmapsapi.geocoder.GeocodeService
import com.tetsoft.staticmapsapi.geocoder.adapter.GeoObjectAdapter
import com.tetsoft.staticmapsapi.geocoder.metadata.FeatureMember
import com.tetsoft.staticmapsapi.geocoder.metadata.GeocoderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://geocode-maps.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        binding.buttonSearch.setOnClickListener {
            setupCoordinatesStr(retrofit)
        }
    }

    private fun setupCoordinatesStr(retrofit : Retrofit) {
        val geocodeService : GeocodeService = retrofit.create(GeocodeService::class.java)
        val coordinates = geocodeService.getCoordinatesFromAddress(
                Config.GEOCODER_API,
                binding.etAddress.text.toString())
        coordinates.enqueue(object : Callback<GeocoderResponse?> {
            override fun onResponse(call: Call<GeocoderResponse?>, response: Response<GeocoderResponse?>) {
                if (response.isSuccessful) {
                    val foundGeoObjectsList =
                        response.body()?.response?.GeoObjectCollection?.featureMember
                    loadMatches(foundGeoObjectsList!!)
                }
            }

            override fun onFailure(call: Call<GeocoderResponse?>, t: Throwable) {
                showSnackbar(t.localizedMessage!!)
            }
        })
    }

    fun loadMatches(foundGeoObjectsList : List<FeatureMember>) {
        binding.rvFoundObjects.adapter = GeoObjectAdapter(foundGeoObjectsList)
        val layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        binding.rvFoundObjects.layoutManager = layoutManager
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_animation)
        binding.rvFoundObjects.startAnimation(fadeIn)
    }

    fun showSnackbar(text : String) {
        Snackbar.make(
            binding.root, text, Snackbar.LENGTH_LONG).show()
    }
}