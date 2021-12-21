package com.tetsoft.staticmapsapi.geocoder.metadata

data class Address(
    val Components: List<Component>,
    val country_code: String,
    val formatted: String
)