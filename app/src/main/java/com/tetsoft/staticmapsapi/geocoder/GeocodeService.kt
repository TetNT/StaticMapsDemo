package com.tetsoft.staticmapsapi.geocoder

import com.tetsoft.staticmapsapi.geocoder.metadata.GeocoderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeService {
    @GET("1.x/?format=json")
    fun getCoordinatesFromAddress(@Query("apikey") apikey : String,
                                  @Query("geocode") address : String) : Call<GeocoderResponse>
}
