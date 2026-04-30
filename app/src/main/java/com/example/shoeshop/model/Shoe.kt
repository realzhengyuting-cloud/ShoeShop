package com.example.shoeshop.model

data class Shoe(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val imageUrl: String,
    val description: String = ""
)
