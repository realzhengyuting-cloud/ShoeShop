package com.example.shoeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.runtime.*
import com.example.shoeshop.model.CartItem
import com.example.shoeshop.model.Shoe
import com.example.shoeshop.ui.screens.CartScreen
import com.example.shoeshop.ui.screens.DetailScreen
import com.example.shoeshop.ui.screens.HomeScreen
import com.example.shoeshop.ui.theme.ShoeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ShoeShopTheme {
                ShoeShopApp()
            }
        }
    }
}

enum class Screen {
    Home, Detail, Cart
}

@Composable
fun ShoeShopApp() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }
    var selectedShoe by remember { mutableStateOf<Shoe?>(null) }
    var cartItems by remember { mutableStateOf(listOf<CartItem>()) }

    val cartItemCount = cartItems.sumOf { it.quantity }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            when (targetState) {
                Screen.Detail, Screen.Cart -> {
                    fadeIn(initialAlpha = 0.3f) + slideInHorizontally { it / 4 } togetherWith
                            fadeOut(targetAlpha = 0.3f) + slideOutHorizontally { -it / 4 }
                }
                Screen.Home -> {
                    fadeIn(initialAlpha = 0.3f) + slideInHorizontally { -it / 4 } togetherWith
                            fadeOut(targetAlpha = 0.3f) + slideOutHorizontally { it / 4 }
                }
            }
        },
        label = "screen_transition"
    ) { screen ->
        when (screen) {
            Screen.Home -> HomeScreen(
                cartItemCount = cartItemCount,
                onShoeClick = { shoe ->
                    selectedShoe = shoe
                    currentScreen = Screen.Detail
                },
                onCartClick = { currentScreen = Screen.Cart }
            )

            Screen.Detail -> selectedShoe?.let { shoe ->
                DetailScreen(
                    shoe = shoe,
                    onBackClick = { currentScreen = Screen.Home },
                    onAddToCart = { addedShoe ->
                        val existing = cartItems.find { it.shoe.id == addedShoe.id }
                        cartItems = if (existing != null) {
                            cartItems.map {
                                if (it.shoe.id == addedShoe.id) it.copy(quantity = it.quantity + 1)
                                else it
                            }
                        } else {
                            cartItems + CartItem(addedShoe)
                        }
                        currentScreen = Screen.Cart
                    }
                )
            }

            Screen.Cart -> CartScreen(
                cartItems = cartItems,
                onBackClick = { currentScreen = Screen.Home },
                onIncrement = { shoeId ->
                    cartItems = cartItems.map {
                        if (it.shoe.id == shoeId) it.copy(quantity = it.quantity + 1)
                        else it
                    }
                },
                onDecrement = { shoeId ->
                    cartItems = cartItems.mapNotNull {
                        if (it.shoe.id == shoeId) {
                            if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
                        } else it
                    }
                },
                onRemove = { shoeId ->
                    cartItems = cartItems.filter { it.shoe.id != shoeId }
                }
            )
        }
    }
}
