package com.tetsoft.staticmapsapi.geocoder.metadata

data class Country(
    val AddressLine: String,
    val AdministrativeArea: AdministrativeArea,
    val Country: CountryX,
    val CountryName: String,
    val CountryNameCode: String,
    val Thoroughfare: ThoroughfareX
)