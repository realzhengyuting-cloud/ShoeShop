package com.example.shoeshop.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class DetailRoute(val shoeId: Int)

@Serializable
object CartRoute
